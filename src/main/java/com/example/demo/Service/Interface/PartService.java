package com.example.demo.Service.Interface;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface PartService extends CommonService<Part, Long> {
    List<Part> listAllByKeyword(String keyword);
    boolean partInUse(long id);
    List<Part> findAllPartsNotIncludedInProduct(Product product);
}
