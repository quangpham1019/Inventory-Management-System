package com.example.demo.Service.PartService;

import com.example.demo.Domain.Part;
import com.example.demo.Repository.PartRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 */

@Service
public class PartServiceWithCRUD extends CommonServiceWithCRUD<Part, Long> implements PartService {
    private PartRepository partRepository;

    public PartServiceWithCRUD(PartRepository partRepository) {
        super(partRepository);
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> listAllByKeyword(String keyword) {
        if (keyword != null) {
            return partRepository.search(keyword);
        }
        return (List<Part>) partRepository.findAll();
    }
}
