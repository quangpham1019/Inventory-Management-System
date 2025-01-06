package com.example.demo.Repository.CRUDRepository;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.PaymentMethod;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    List<Order> findAllByCustomer_LastName(String customerLastName);
    List<Order> findAllByPaymentMethod(PaymentMethod paymentMethod);

}
