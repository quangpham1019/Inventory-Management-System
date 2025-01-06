package com.example.demo.Service;

import com.example.demo.Domain.Customer;

import java.util.List;

public interface CustomerService extends CommonService<Customer, Long> {
    List<Customer> listAllByKeyword(String keyword);
    boolean hasCustomer();
}
