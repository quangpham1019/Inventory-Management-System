package com.example.demo.service;

import com.example.demo.domain.Service;
import com.example.demo.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Service
public class JcsServiceServiceImpl implements JcsServiceService{

    private ServiceRepository serviceRepository;

    @Autowired
    public JcsServiceServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }


    @Override
    public Service findById(int serviceId) {
        Optional<Service> service = serviceRepository.findById((long) serviceId);
        return service.orElseThrow(() -> new IllegalArgumentException("Could not find service with id: " + serviceId));
    }

    @Override
    public List<Service> listAllByKeyword(String keyword) {
        if(keyword != null) {
            return serviceRepository.search(keyword);
        }
        return (List<Service>) serviceRepository.findAll();
    }
    @Override
    public void save(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public void saveAll(List<Service> serviceList) {
        serviceRepository.saveAll(serviceList);
    }

    @Override
    public void deleteById(int serviceId) {
        serviceRepository.deleteById((long) serviceId);
    }

    @Override
    public boolean hasService() {
        return serviceRepository.numService()!=0;
    }
}
