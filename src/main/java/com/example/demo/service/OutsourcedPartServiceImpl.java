package com.example.demo.service;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.repositories.OutsourcedPartRepository;
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
public class OutsourcedPartServiceImpl implements OutsourcedPartService{
    private OutsourcedPartRepository partRepository;

    public OutsourcedPartServiceImpl(OutsourcedPartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<OutsourcedPart> findAll() {
        return (List<OutsourcedPart>) partRepository.findAll();
    }

    @Override
    public OutsourcedPart findById(int partId) {
        Optional<OutsourcedPart> outsourcedPart = partRepository.findById((long) partId);

        return outsourcedPart.orElseThrow(() -> new IllegalArgumentException("Could not find part with id: " + partId));
    }

    @Override
    public void save(OutsourcedPart thePart) {
        partRepository.save(thePart);

    }

    @Override
    public void deleteById(int theId) {
        Long theIdl=(long)theId;
        partRepository.deleteById(theIdl);
    }

}
