package com.example.demo.Service.ItemService;

import com.example.demo.Domain.Item;
import com.example.demo.Repository.ItemRepository;
import com.example.demo.Service.CommonService.CommonServiceWithCRUD;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceWithCRUD extends CommonServiceWithCRUD<Item, Long> implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceWithCRUD(ItemRepository itemRepository) {
        super(itemRepository);
        this.itemRepository = itemRepository;
    }
}
