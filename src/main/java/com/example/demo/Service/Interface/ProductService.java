package com.example.demo.Service.Interface;

import com.example.demo.Domain.Product;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductService extends CommonService<Product, Long> {
    List<Product> listAllByKeyword(String keyword);
    void removeProduct(long id);
    void associatePartToProduct(Product product, long partId);
    void disassociatePartFromProduct(Product product, long partId);
}
