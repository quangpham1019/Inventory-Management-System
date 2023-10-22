package com.example.demo.service;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */

@Service
public class PartServiceImpl implements PartService {
    private PartRepository partRepository;

    @Autowired
    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> findAll() {
        return (List<Part>) partRepository.findAll();
    }

    @Override
    public List<Part> listAllByKeyword(String keyword) {
        if (keyword != null) {
            return partRepository.search(keyword);
        }
        return (List<Part>) partRepository.findAll();
    }

    @Override
    public Part findById(int partId) {
        Optional<Part> part = partRepository.findById((long) partId);

        return part.orElseThrow(() -> new IllegalArgumentException("Could not find part with id: " + partId));
    }

    @Override
    public void save(Part thePart) {
        partRepository.save(thePart);
    }

    @Override
    public void saveAll(List<Part> partList) {
        partRepository.saveAll(partList);
    }

    @Override
    public void deleteById(int partId) {
        partRepository.deleteById((long) partId);
    }
}
