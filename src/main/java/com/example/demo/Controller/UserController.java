package com.example.demo.Controller;

import com.example.demo.Domain.*;
import com.example.demo.Security.AppUser;
import com.example.demo.Service.Interface.CustomerService;
import com.example.demo.Service.Interface.ItemService;
import com.example.demo.Service.Interface.OrderService;
import com.example.demo.Service.Interface.ProductService;
import com.example.demo.Service.Interface.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// TODO: separate UserController into CustomerController, SalesController, ReportController

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Valid
    private Order order;
    private ItemService itemService;
    private ProductService productService;
    private OrderService orderService;
    private ReportService reportService;
    private CustomerService customerService;
    private AppUser appUser;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        this.appUser = appUser;
        return appUser;
    }

    @Autowired
    public UserController(ItemService itemService, ProductService productService, Order order, OrderService orderService, ReportService reportService, CustomerService customerService) {
        this.itemService = itemService;
        this.productService = productService;
        this.order = order;
        this.orderService = orderService;
        this.reportService = reportService;
        this.customerService = customerService;
    }




}
