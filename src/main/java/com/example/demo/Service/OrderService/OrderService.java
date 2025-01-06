package com.example.demo.Service.OrderService;

import com.example.demo.Domain.Order;
import com.example.demo.Service.CommonService.CommonService;

import java.util.List;

public interface OrderService extends CommonService<Order, Long> {
    List<Order> findAllBy(String filterCriteria, String keyword);
}