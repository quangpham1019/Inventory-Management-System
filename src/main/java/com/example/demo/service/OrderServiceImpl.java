package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.PaymentMethod;
import com.example.demo.domain.Report;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> findAllBy(String filterCriteria, String orderKeyword) {
        switch (filterCriteria){
            case "paymentMethod":
                return orderRepository.findAllByPaymentMethod(PaymentMethod.valueOf(orderKeyword));
            case "customer":
                return orderRepository.findAllByCustomer_LastName(orderKeyword);
        }
        return orderRepository.findAll();
    }

    @Override
    public void save(Order order) {
        orderRepository.save(order);
    }
}
