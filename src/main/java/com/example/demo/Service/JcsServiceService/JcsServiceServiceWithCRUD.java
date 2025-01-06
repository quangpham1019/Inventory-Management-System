package com.example.demo.Service.JcsServiceService;

import com.example.demo.Domain.Service;
import com.example.demo.Repository.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class JcsServiceServiceWithCRUD implements JcsServiceService {

    private ServiceRepository serviceRepository;

    public JcsServiceServiceWithCRUD(ServiceRepository serviceRepository) {
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
