package com.example.demo.repositories;

import com.example.demo.domain.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.lastName LIKE %?1%")
    List<Customer> search(String keyword);

    @Query("SELECT COUNT(*) FROM Customer")
    int numCustomer();

}
