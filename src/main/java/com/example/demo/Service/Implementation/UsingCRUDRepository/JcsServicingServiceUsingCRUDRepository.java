package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.JcsServicing;
import com.example.demo.Repository.CRUDRepository.JcsServicingRepository;
import com.example.demo.Service.Interface.JcsServicingService;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JcsServicingServiceUsingCRUDRepository implements JcsServicingService {

    private JcsServicingRepository jcsServicingRepository;

    public JcsServicingServiceUsingCRUDRepository(JcsServicingRepository jcsServicingRepository) {
        this.jcsServicingRepository = jcsServicingRepository;
    }


    @Override
    public JcsServicing findById(int serviceId) {
        Optional<JcsServicing> service = jcsServicingRepository.findById((long) serviceId);
        return service.orElseThrow(() -> new IllegalArgumentException("Could not find service with id: " + serviceId));
    }

    @Override
    public List<JcsServicing> listAllByKeyword(String keyword) {
        if(keyword != null) {
            return jcsServicingRepository.search(keyword);
        }
        return (List<JcsServicing>) jcsServicingRepository.findAll();
    }
    @Override
    public void save(JcsServicing jcsServicing) {
        jcsServicingRepository.save(jcsServicing);
    }

    @Override
    public void saveAll(List<JcsServicing> jcsServicingList) {
        jcsServicingRepository.saveAll(jcsServicingList);
    }

    @Override
    public void deleteById(int serviceId) {
        jcsServicingRepository.deleteById((long) serviceId);
    }

    @Override
    public boolean hasService() {
        return jcsServicingRepository.numService()!=0;
    }
}
