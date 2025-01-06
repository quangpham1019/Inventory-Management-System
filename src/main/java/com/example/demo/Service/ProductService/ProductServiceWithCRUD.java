package com.example.demo.Service.ProductService;

import com.example.demo.Domain.Product;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 *
 *
 */
@Service
public class ProductServiceWithCRUD extends CommonServiceWithCRUD<Product, Long> implements ProductService {
    private ProductRepository productRepository;

    public ProductServiceWithCRUD(ProductRepository productRepository) {
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
