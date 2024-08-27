package com.example.demo.UnitTest;

import com.example.demo.Controller.ProductController;
import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Service.PartService;
import com.example.demo.Service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class ControllerTest {

    // ProductController
    @Test
    public void filterAvailablePartsTest() {

        // Arrange
        PartService partService = Mockito.mock(PartService.class);
        ProductService productService = Mockito.mock(ProductService.class);
        ProductController productController = new ProductController(partService, productService);
        Model model = Mockito.mock(Model.class);
        Product product = Mockito.mock(Product.class);

        Set<Part> associatedParts = Set.of(
                Mockito.mock(Part.class),
                Mockito.mock(Part.class));
        List<Part> availableParts_expected = List.of(
                Mockito.mock(Part.class),
                Mockito.mock(Part.class),
                Mockito.mock(Part.class));

        List<Part> allParts = new ArrayList<>();
        allParts.addAll(associatedParts);
        allParts.addAll(availableParts_expected);

        Mockito.when(partService.findAll()).thenReturn(allParts);
        Mockito.when(product.getParts()).thenReturn(associatedParts);

        // Act
        productController.filterAvailableParts(model, product);

        // Assert
        Mockito.verify(model, Mockito.times(1)).addAttribute("availableParts", availableParts_expected);
        Mockito.verify(model, Mockito.times(1)).addAttribute("associatedParts", associatedParts);
    }
}
