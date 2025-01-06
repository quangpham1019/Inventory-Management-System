package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Repository.CRUDRepository.ProductRepository;
import com.example.demo.Service.Interface.PartService;
import com.example.demo.Service.Interface.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 *
 *
 */
@Service
public class ProductServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Product, Long> implements ProductService {
    private final ProductRepository productRepository;
    private final PartService partService;

    public ProductServiceUsingCRUDRepository(ProductRepository productRepository, PartService partService) {
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
}
