package com.example.demo.Service;

import com.example.demo.Domain.Part;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface PartService {
    List<Part> findAll();
    Part findById(int theId);
    void save (Part thePart);
    void saveAll(List<Part> partList);
    void deleteById(int theId);
    List<Part> listAllByKeyword(String keyword);
}
