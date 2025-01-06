package com.example.demo.Controller;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Service.Interface.ItemService;
import com.example.demo.Service.Interface.PartService;
import com.example.demo.Service.Interface.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 *
 *
 */
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final Order order;
    private final PartService partService;
    private final ProductService productService;
    private final ItemService itemService;

    private static Product curProduct;

    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model model) {
        curProduct = new Product();
        model.addAttribute("product", curProduct);
        model.addAttribute("availableParts", partService.findAllPartsNotIncludedInProduct(curProduct));
        model.addAttribute("associatedParts", curProduct.getParts());

        return "form/product_form";
    }

    @PostMapping("/processFormAddProduct")
    public String submitForm(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        model.addAttribute("product", product);

        if(bindingResult.hasErrors()){

            model.addAttribute("availableParts", partService.findAllPartsNotIncludedInProduct(curProduct));
            model.addAttribute("associatedParts", curProduct.getParts());

            return "form/product_form";
        }

        if(product.getId()!=0) {
            productService.adjustProductQuantity(product);
        }
        else {
            product.setInv(0);
        }

        productService.save(product);
        return "confirmation/confirmationaddproduct";

    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int productId, Model model) {
        curProduct = productService.findById((long) productId);

        model.addAttribute("product", curProduct);
        model.addAttribute("availableParts", partService.findAllPartsNotIncludedInProduct(curProduct));
        model.addAttribute("associatedParts", curProduct.getParts());

        return "form/product_form";
    }

    @GetMapping("/removeProduct")
    public String removeProduct(@RequestParam("productID") int productId) {

        if (itemService.itemExistsInOrder(productId, order)) {
            return "error/error_item_in_order";
        }

        productService.removeProduct(productId);

        return "confirmation/confirmationdeleteproduct";
    }

    // TODO: use js to manipulate adding/removing associate parts on frontend
    //      receive a list of associatedParts in backend
    //      process the list and add associated parts accordingly
    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int partId, Model model){

        if (curProduct.getName()==null) {
            return "error/error_save_product_first";
        }

        productService.associatePartToProduct(curProduct, partId);

        model.addAttribute("product", curProduct);
        model.addAttribute("availableParts", partService.findAllPartsNotIncludedInProduct(curProduct));
        model.addAttribute("associatedParts", curProduct.getParts());

        return "form/product_form";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int partId, Model model){

        productService.disassociatePartFromProduct(curProduct, partId);

        model.addAttribute("product", curProduct);
        model.addAttribute("availableParts", partService.findAllPartsNotIncludedInProduct(curProduct));
        model.addAttribute("associatedParts", curProduct.getParts());

        return "form/product_form";
    }
}
