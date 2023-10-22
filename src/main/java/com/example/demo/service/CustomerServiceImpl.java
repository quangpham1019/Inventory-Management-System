package com.example.demo.service;

import com.example.demo.domain.Customer;
import com.example.demo.repositories.CustomerRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
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
    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
    @Override
    public void saveAll(List<Customer> customerSet) {
        customerRepository.saveAll(customerSet);
    }
    @Override
    public Customer findById(int customerId) {
        Optional<Customer> customer = customerRepository.findById((long) customerId);

        return customer.orElseThrow(() -> new IllegalArgumentException("Could not find customer with id: " + customerId));
    }
    @Override
    public List<Customer> findAll() {
        return (List<Customer>) customerRepository.findAll();
    }
    @Override
    public void deleteById(int customerId) {
        customerRepository.deleteById((long) customerId);
    }
}
