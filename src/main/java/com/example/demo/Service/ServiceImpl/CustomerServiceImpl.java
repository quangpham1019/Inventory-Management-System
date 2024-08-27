package com.example.demo.Service.ServiceImpl;

import com.example.demo.Domain.Customer;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

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
