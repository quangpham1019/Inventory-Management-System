package com.example.demo.UnitTest;

import com.example.demo.Domain.Item;
import com.example.demo.Domain.Order;
import com.example.demo.Domain.Product;
import com.example.demo.Repository.CRUDRepository.ItemRepository;
import com.example.demo.Service.Data.Implementation.UsingCRUDRepository.ItemServiceUsingCRUDRepository;
import com.example.demo.Service.Data.Interface.ItemService;
import com.example.demo.Service.Data.Interface.ProductService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ItemServiceUsingCRUDRepositoryTest {

    @Test
    public void testItemExistsInOrder() {

    }

    @Test
    public void testAdjustItemQuantityInInventory() {
        // ARRANGE
        ProductService productService = Mockito.mock(ProductService.class);
        ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
        Product product = new Product("product1", 15, 2);
        int increaseInventory = 1;
        int decreaseInventory = -1;

        ItemService itemService = new ItemServiceUsingCRUDRepository(itemRepository, productService);

        Mockito.when(productService.findById(Mockito.any(Long.class))).thenReturn(product);

        // ACT
        itemService.adjustItemQuantityInInventory(product, decreaseInventory);
        var expectedResult1 = 1;
        Assertions.assertEquals(expectedResult1, product.getInv());

        itemService.adjustItemQuantityInInventory(product, decreaseInventory);
        var expectedResult2 = 0;
        Assertions.assertEquals(expectedResult2, product.getInv());

        itemService.adjustItemQuantityInInventory(product, decreaseInventory);
        var expectedResult3 = 0;
        Assertions.assertEquals(expectedResult3, product.getInv());

        itemService.adjustItemQuantityInInventory(product, increaseInventory);
        var expectedResult4 = 1;
        Assertions.assertEquals(expectedResult4, product.getInv());

        // ASSERT
        Mockito.verify(productService, Mockito.times(3)).save(Mockito.any(Product.class));
    }
}
