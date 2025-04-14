package com.example.demo.Service.Data.Implementation;

import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */

@Service
public class PartServiceImpl extends CommonServiceImpl<Part, Long> implements com.example.demo.Service.Data.PartService {
    private PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
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

    @Override
    public boolean partInUse(long id) {
        Part part = findById(id);
        return !part.getProducts().isEmpty();
    }

    @Override
    public List<Part> findAllPartsNotIncludedInProduct(Product product) {

        return findAll()
                .stream()
                .filter(part -> !part.getProducts().contains(product))
                .toList();
    }
}
