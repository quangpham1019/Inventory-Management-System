package com.example.demo.repositories;

import com.example.demo.domain.Order;
import com.example.demo.domain.PaymentMethod;
import com.example.demo.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer_LastName(String customerLastName);
    List<Order> findAllByPaymentMethod(PaymentMethod paymentMethod);

}
