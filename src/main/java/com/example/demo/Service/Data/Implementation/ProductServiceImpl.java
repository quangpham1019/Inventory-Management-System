package com.example.demo.Service.Data.Implementation;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.Data.PartService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 *
 *
 */
@Service
public class ProductServiceImpl extends CommonServiceImpl<Product, Long> implements com.example.demo.Service.Data.ProductService {
    private final ProductRepository productRepository;
    private final PartService partService;

    public ProductServiceImpl(ProductRepository productRepository, PartService partService) {
        super(productRepository);
        this.productRepository = productRepository;
        this.partService = partService;
    }

    @Override
    public List<Product> listAllByKeyword(String keyword){
        if(keyword !=null){
            return productRepository.search(keyword);
        }
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void removeProduct(long id) {
        // remove parts from current product
        // partService.disassociatePartsFromDeletingProduct(Product)
        Product deletingProduct = findById(id);

        for (Part part : deletingProduct.getParts()) {
            part.getProducts().remove(deletingProduct);
            partService.save(part);
        }

        // remove parts from current product to prevent cascade delete
        deletingProduct.getParts().removeAll(deletingProduct.getParts());
        save(deletingProduct);
        deleteById(id);
    }

    @Override
    public void associatePartToProduct(Product product, long partId) {

        Part part = partService.findById(partId);

        product.getParts().add(part);
        part.getProducts().add(product);
        save(product);
        partService.save(part);
    }

    @Override
    public void disassociatePartFromProduct(Product product, long partId) {
        Part part = partService.findById(partId);

        product.getParts().remove(part);
        part.getProducts().remove(product);
        save(product);
        partService.save(part);
    }

    @Override
    public void adjustProductQuantity(Product updatingProduct) {
        Product productInDb = findById(updatingProduct.getId());
        int updatingProductInv = updatingProduct.getInv();
        int productInDbInv = productInDb.getInv();

        // increase associated parts count if updatingProductInv < productInDbInv
        // decrease if updatingProductInv > productInDbInv
        // add (productInDbInv - updatingProductInv) to associated parts count
        int changeInAssociatedPartsCount = productInDbInv - updatingProductInv;
        for (Part part : productInDb.getParts()) {
            int prevPartInv = part.getInv();
            part.setInv(prevPartInv + changeInAssociatedPartsCount);
            partService.save(part);
        }
    }
}
