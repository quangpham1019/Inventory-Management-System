package com.example.demo.Repository;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCustomer_LastName(String customerLastName);
    List<Order> findAllByPaymentMethod(PaymentMethod paymentMethod);

}
