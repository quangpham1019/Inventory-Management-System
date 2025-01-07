package com.example.demo.Service.Data.Interface;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.Order;
import com.example.demo.Domain.OrderItem;

import java.util.List;

public interface OrderService extends CommonService<Order, Long> {
    List<Order> findAllBy(String filterCriteria, String keyword);
    void addItemToOrder(Order order, Item item);
    void adjustItemQuantityInOrder(Order order, long itemId, int changeQuantity);
}