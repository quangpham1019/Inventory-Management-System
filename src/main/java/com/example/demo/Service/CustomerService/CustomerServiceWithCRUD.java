package com.example.demo.Service.CustomerService;

import com.example.demo.Domain.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceWithCRUD extends CommonServiceWithCRUD<Customer, Long> implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceWithCRUD(CustomerRepository customerRepository) {
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> listAllByKeyword(String keyword) {
        if (keyword != null) {
            return customerRepository.search(keyword);
        }
        return (List<Customer>) customerRepository.findAll();
    }
    @Override
    public boolean hasCustomer() {
        return customerRepository.numCustomer()!=0;
    }
}
