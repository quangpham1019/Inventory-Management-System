package com.example.demo.Service.Data.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.Order;
import com.example.demo.Repository.CRUDRepository.ItemRepository;
import com.example.demo.Service.Data.Interface.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Item, Long> implements ItemService {

    private final ItemRepository itemRepository;

    public ItemServiceUsingCRUDRepository(ItemRepository itemRepository) {
        super(itemRepository);
        this.itemRepository = itemRepository;
    }

    @Override
    public boolean itemExistsInOrder(long id, Order order) {
        Item item = findById(id);

        return order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(item));
    }
}
