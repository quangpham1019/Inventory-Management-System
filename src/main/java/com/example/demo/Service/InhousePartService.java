package com.example.demo.Service;

import com.example.demo.Domain.InhousePart;

import java.util.List;

/**
 *
 *
 *
 *
 */
public interface InhousePartService {
    public List<InhousePart> findAll();
    public InhousePart findById(int theId);
    public void save (InhousePart thePart);
    public void deleteById(int theId);
}
