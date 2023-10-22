package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.domain.Part;

import java.util.List;
import java.util.Set;

public interface CustomerService {
    List<Customer> findAll();
    List<Customer> listAllByKeyword(String keyword);
    boolean hasCustomer();
    void save(Customer customer);
    void saveAll(List<Customer> customerSet);
    Customer findById(int customerId);

    void deleteById(int customerId);
}
