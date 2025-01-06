package com.example.demo.Service;

import com.example.demo.Domain.Order;

import java.util.List;

public interface OrderService extends CommonService<Order, Long> {
    List<Order> findAllBy(String filterCriteria, String keyword);
}