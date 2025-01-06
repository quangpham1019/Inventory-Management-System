package com.example.demo.Service.Interface;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.Order;

public interface ItemService extends CommonService<Item, Long> {
    boolean itemExistsInOrder(long id, Order order);
}
