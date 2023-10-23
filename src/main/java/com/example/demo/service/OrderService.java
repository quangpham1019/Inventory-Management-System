package com.example.demo.service;

import com.example.demo.domain.Order;
import com.example.demo.domain.Report;

import java.util.List;

public interface OrderService {

    List<Order> findAllBy(String filterCriteria, String keyword);
    void save(Order order);
}
