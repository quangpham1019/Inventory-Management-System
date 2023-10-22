package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.domain.Service;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.JcsServiceService;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 *
 *
 *
 *
 */

@Controller
public class InventoryController {

    private PartService partService;
    private ProductService productService;
    private UserRepository userRepository;
    private JcsServiceService jcsServiceService;

    public InventoryController(PartService partService, ProductService productService, UserRepository userRepository, JcsServiceService jcsServiceService){
        this.partService=partService;
        this.productService=productService;
        this.userRepository = userRepository;
        this.jcsServiceService = jcsServiceService;
    }
    @GetMapping("")
    public String redirect() {
        return "redirect:/inventory";
    }
    @GetMapping("/inventory")
    public String listPartsAndProducts(
            Model theModel,
            @Param("partkeyword") String partkeyword,
            @Param("productkeyword") String productkeyword,
            @Param("serviceKeyword") String serviceKeyword){

        List<Part> partList = partService.listAllByKeyword(partkeyword);
        theModel.addAttribute("parts",partList);
        theModel.addAttribute("partkeyword",partkeyword);

    //    theModel.addAttribute("products",productService.findAll());
        List<Product> productList = productService.listAll(productkeyword);
        theModel.addAttribute("products", productList);
        theModel.addAttribute("productkeyword",productkeyword);

        List<Service> serviceList = jcsServiceService.listAllByKeyword(serviceKeyword);
        theModel.addAttribute("serviceList", serviceList);
        theModel.addAttribute("serviceKeyword", serviceKeyword);

        theModel.addAttribute("disabled", true);
        System.out.println(serviceList);
        return "inventory";
    }
}
