package com.example.demo.service;

import com.example.demo.domain.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{

    private ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item findById(int itemId) {
        Optional<Item> item = itemRepository.findById((long) itemId);

        return item.orElseThrow(() -> new IllegalArgumentException("Could not find item with id: " + itemId));
    }
}
