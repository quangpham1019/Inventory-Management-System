package com.example.demo.Service.ServiceImpl;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.PaymentMethod;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

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
