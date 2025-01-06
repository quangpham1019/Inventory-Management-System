package com.example.demo.Service.ProductService;

import com.example.demo.Domain.Product;
import com.example.demo.Service.CommonService.CommonService;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface ProductService extends CommonService<Product, Long> {
    List<Product> listAllByKeyword(String keyword);
}
