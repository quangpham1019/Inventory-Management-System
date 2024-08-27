package com.example.demo.Service.ServiceImpl;

import com.example.demo.Domain.InhousePart;
import com.example.demo.Repository.InhousePartRepository;
import com.example.demo.Service.InhousePartService;
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
public class InhousePartServiceImpl implements InhousePartService {
    private InhousePartRepository partRepository;

    public InhousePartServiceImpl(InhousePartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public List<InhousePart> findAll() {
        return (List<InhousePart>) partRepository.findAll();
    }

    @Override
    public InhousePart findById(int partId) {
        Optional<InhousePart> inhousePart = partRepository.findById((long) partId);

        return inhousePart.orElseThrow(() -> new IllegalArgumentException("Could not find part with id: " + partId));
    }

    @Override
    public void save(InhousePart inhousePart) {
        partRepository.save(inhousePart);

    }

    @Override
    public void deleteById(int partId) {
        partRepository.deleteById((long) partId);
    }

}
