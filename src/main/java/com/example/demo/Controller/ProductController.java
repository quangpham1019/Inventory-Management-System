package com.example.demo.Controller;

import com.example.demo.Domain.Order;
import com.example.demo.Domain.Part;
import com.example.demo.Domain.Product;
import com.example.demo.Service.PartService;
import com.example.demo.Service.ProductService;
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
public class ProductController {
    @Autowired
    private Order order;

    private static Product curProduct;
    private PartService partService;
    private ProductService productService;

    public ProductController(PartService partService, ProductService productService) {
        this.partService = partService;
        this.productService = productService;
    }
    @GetMapping("/showFormAddProduct")
    public String showFormAddPart(Model model) {
        curProduct = new Product();
        model.addAttribute("product", curProduct);
        model.addAttribute("parts", partService.findAll());

        filterAvailableParts(model, curProduct);

        return "product_form";
    }

    @PostMapping("/showFormAddProduct")
    public String submitForm(@Valid @ModelAttribute Product product, BindingResult bindingResult, Model model) {
        model.addAttribute("product", product);

        if(bindingResult.hasErrors()){

            filterAvailableParts(model, product);
            model.addAttribute("parts", partService.findAll());

            return "product_form";
        }

        if(product.getId()!=0) {
            Product oldProduct = productService.findById((int)product.getId());
            if(product.getInv() - oldProduct.getInv()>0) {
                for (Part p : oldProduct.getParts()) {
                    int inv = p.getInv();
                    p.setInv(inv - (product.getInv() - oldProduct.getInv()));
                    partService.save(p);
                }
            }
        }
        else {
            product.setInv(0);
        }

        productService.save(product);
        return "confirmation/confirmationaddproduct";

    }

    @GetMapping("/showProductFormForUpdate")
    public String showProductFormForUpdate(@RequestParam("productID") int productId, Model model) {
        curProduct = productService.findById(productId);

        model.addAttribute("product", curProduct);
        model.addAttribute("parts", partService.findAll());

        filterAvailableParts(model, curProduct);

        return "product_form";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int productId) {

        Product deletingProduct = productService.findById(productId);
        if (order.getOrderItemSet()
                .stream()
                .anyMatch(orderItem -> orderItem.getItem().equals(deletingProduct))) {

            return "error/error_product_in_order";
        }

        for (Part part : deletingProduct.getParts()){
            part.getProducts().remove(deletingProduct);
            partService.save(part);
        }

        deletingProduct.getParts().removeAll(deletingProduct.getParts());
        productService.save(deletingProduct);
        productService.deleteById(productId);

        return "confirmation/confirmationdeleteproduct";
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int partId, Model model){

        if (curProduct.getName()==null) {
            return "error/error_save_product_first";
        }

        Part curPart = partService.findById(partId);

        curProduct.getParts().add(curPart);
        curPart.getProducts().add(curProduct);
        productService.save(curProduct);
        partService.save(curPart);

        model.addAttribute("product", curProduct);
        filterAvailableParts(model, curProduct);

        return "product_form";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int partId, Model model){

        Part curPart = partService.findById(partId);

        curProduct.getParts().remove(curPart);
        curPart.getProducts().remove(curProduct);
        productService.save(curProduct);
        partService.save(curPart);

        model.addAttribute("product", curProduct);
        filterAvailableParts(model, curProduct);

        return "product_form";
    }

    public void filterAvailableParts(Model model, Product product) {
        List<Part> availableParts = new ArrayList<>();

        for(Part p: partService.findAll()){
            if(!product.getParts().contains(p)) availableParts.add(p);
        }

        model.addAttribute("availableParts", availableParts);
        model.addAttribute("associatedParts", product.getParts());
    }
}
