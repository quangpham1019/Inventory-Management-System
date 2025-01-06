package com.example.demo.Service.JcsServiceService;

import com.example.demo.Domain.Service;
import com.example.demo.Repository.JcsServicingRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JcsServicingServiceWithCRUD implements JcsServicingService {

    private JcsServicingRepository jcsServicingRepository;

    public JcsServicingServiceWithCRUD(JcsServicingRepository jcsServicingRepository) {
        this.jcsServicingRepository = jcsServicingRepository;
    }


    @Override
    public Service findById(int serviceId) {
        Optional<Service> service = jcsServicingRepository.findById((long) serviceId);
        return service.orElseThrow(() -> new IllegalArgumentException("Could not find service with id: " + serviceId));
    }

    @Override
    public List<Service> listAllByKeyword(String keyword) {
        if(keyword != null) {
            return jcsServicingRepository.search(keyword);
        }
        return (List<Service>) jcsServicingRepository.findAll();
    }
    @Override
    public void save(Service service) {
        jcsServicingRepository.save(service);
    }

    @Override
    public void saveAll(List<Service> serviceList) {
        jcsServicingRepository.saveAll(serviceList);
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
