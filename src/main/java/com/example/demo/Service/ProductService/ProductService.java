package com.example.demo.Service.ProductService;

import com.example.demo.Domain.Product;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductService {
    List<Product> findAll();
    Product findById(int theId);
    void save (Product theProduct);
    void deleteById(int theId);
    List<Product> listAll(String keyword);
    void saveAll(List<Product> productList);
}
