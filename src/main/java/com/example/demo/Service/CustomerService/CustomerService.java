package com.example.demo.Service.CustomerService;

import com.example.demo.Domain.Customer;
import com.example.demo.Service.CommonService.CommonService;

import java.util.List;

public interface CustomerService extends CommonService<Customer, Long> {
    List<Customer> listAllByKeyword(String keyword);
    boolean hasCustomer();
}
