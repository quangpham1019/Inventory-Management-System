package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Product;
import com.example.demo.Repository.CRUDRepository.ProductRepository;
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
    private ProductRepository productRepository;

    public ProductServiceUsingCRUDRepository(ProductRepository productRepository) {
        super(productRepository);
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> listAllByKeyword(String keyword){
        if(keyword !=null){
            return productRepository.search(keyword);
        }
        return (List<Product>) productRepository.findAll();
    }
}
