package com.example.demo.Service;

import com.example.demo.Domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    List<Customer> listAllByKeyword(String keyword);
    boolean hasCustomer();
    void save(Customer customer);
    void saveAll(List<Customer> customerSet);
    Customer findById(int customerId);

    void deleteById(int customerId);
}
