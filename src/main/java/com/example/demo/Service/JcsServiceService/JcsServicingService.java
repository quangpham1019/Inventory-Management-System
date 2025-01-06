package com.example.demo.Service.JcsServiceService;

import com.example.demo.Domain.Service;

import java.util.List;

public interface JcsServicingService {

    Service findById(int serviceId);
    List<Service> listAllByKeyword(String keyword);
    void save(Service service);
    void saveAll(List<Service> serviceList);
    void deleteById(int serviceId);
    boolean hasService();
}
