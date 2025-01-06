package com.example.demo.Service.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Item;
import com.example.demo.Repository.CRUDRepository.ItemRepository;
import com.example.demo.Service.Interface.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Item, Long> implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceUsingCRUDRepository(ItemRepository itemRepository) {
        super(itemRepository);
        this.itemRepository = itemRepository;
    }
}
