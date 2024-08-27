package com.example.demo.Service.ServiceImpl;

import com.example.demo.Domain.Item;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Service.ItemService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    private ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item findById(int itemId) {
        Optional<Item> item = itemRepository.findById((long) itemId);

        return item.orElseThrow(() -> new IllegalArgumentException("Could not find item with id: " + itemId));
    }
}
