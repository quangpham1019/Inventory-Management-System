package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Customer;
import com.example.demo.Repository.CRUDRepository.CustomerRepository;
import com.example.demo.Service.Interface.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Customer, Long> implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceUsingCRUDRepository(CustomerRepository customerRepository) {
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
