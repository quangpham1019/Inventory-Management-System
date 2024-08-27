package com.example.demo.Service;

import com.example.demo.Domain.Order;

import java.util.List;

public interface OrderService {

    List<Order> findAllBy(String filterCriteria, String keyword);
    void save(Order order);
}
