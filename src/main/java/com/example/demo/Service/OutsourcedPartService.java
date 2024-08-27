package com.example.demo.Service;

import com.example.demo.Domain.OutsourcedPart;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface OutsourcedPartService {
        List<OutsourcedPart> findAll();
        OutsourcedPart findById(int theId);
        void save (OutsourcedPart thePart);
        void deleteById(int theId);
}
