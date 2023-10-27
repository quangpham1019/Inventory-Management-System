package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Service;

import java.util.List;
import java.util.Set;

public interface JcsServiceService {

    Service findById(int serviceId);
    List<Service> listAllByKeyword(String keyword);
    void save(Service service);
    void saveAll(List<Service> serviceList);
    void deleteById(int serviceId);
    boolean hasService();
}
