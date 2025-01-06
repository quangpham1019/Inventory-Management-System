package com.example.demo.Service.ServiceImpl;

import com.example.demo.Domain.Item;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceWithCRUD extends CommonServiceWithCRUD<Item, Long> implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceWithCRUD(ItemRepository itemRepository) {
        super(itemRepository);
        this.itemRepository = itemRepository;
    }
}
