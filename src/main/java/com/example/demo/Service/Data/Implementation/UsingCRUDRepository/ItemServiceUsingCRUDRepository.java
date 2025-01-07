package com.example.demo.Service.Data.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.JcsServicing;
import com.example.demo.Domain.Order;
import com.example.demo.Domain.Product;
import com.example.demo.Repository.CRUDRepository.ItemRepository;
import com.example.demo.Service.Data.Interface.ItemService;
import com.example.demo.Service.Data.Interface.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceUsingCRUDRepository extends CommonServiceUsingCRUDRepository<Item, Long> implements ItemService {

    private final ItemRepository itemRepository;
    private final ProductService productService;

    public ItemServiceUsingCRUDRepository(ItemRepository itemRepository, ProductService productService) {
        super(itemRepository);
        this.itemRepository = itemRepository;
        this.productService = productService;
    }

    @Override
    public boolean itemExistsInOrder(long id, Order order) {
        Item item = findById(id);

        return order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(item));
    }

    @Override
    public boolean adjustItemQuantityInInventory(Item item, int changeQuantityInInventory) {
        // TODO: refactor model to accommodate different type of countable items
        //      ie. parts, products, accessories, etc.
        boolean changeQuantityInInventoryDecreasing = changeQuantityInInventory < 0;

        if (!item.getClass().equals(JcsServicing.class)) {
            Product product = productService.findById(item.getId());
            if(product.getInv()==0 && changeQuantityInInventoryDecreasing) return false;

            product.setInv(product.getInv() + changeQuantityInInventory);
            productService.save(product);
        }

        return true;
    }
}
