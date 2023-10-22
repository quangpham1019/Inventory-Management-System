package com.example.demo.service;

import com.example.demo.domain.Item;

import java.util.Optional;

public interface ItemService {
    Item findById(int itemId);
}
