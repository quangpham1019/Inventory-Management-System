package com.example.demo.Controller;

import com.example.demo.Domain.*;
import com.example.demo.Security.AppUser;
import com.example.demo.Service.*;
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

    private static Customer currentCustomer;
    @GetMapping("/sales")
    public String getSalesPage(Model model,
                               @Param("customerKeyword") String customerKeyword,
                               @ModelAttribute String itemError) {
        List<Customer> customerList = customerService.listAllByKeyword(customerKeyword);
        if (!itemError.isBlank()) {
            // string length is less than 12, invalidate int values from range of itemId
            if (itemError.length()<12) {
                Product product = productService.findById(Integer.parseInt(itemError));
                model.addAttribute("errors", "There are not enough products " + product.getName() + " in store.");
            } else {
                model.addAttribute("errors", itemError);
            }
        }

        model.addAttribute("customerList", customerList);
        model.addAttribute("customerKeyword", customerKeyword);
        model.addAttribute("order", order);
        model.addAttribute("selectedCustomer", currentCustomer);
        return "menu pages/sales";
    }

    @PostMapping("/addItemToOrder")
    public String addItemToOrder(@RequestParam int itemId) {

        // create new OrderItem orderItem
        // set Item of orderItem to part retrieved from partService.findById(partId)
        OrderItem orderItem = new OrderItem();
        Item currentItem = itemService.findById(itemId);
        orderItem.setItem(currentItem);
        System.out.println("ADDING ITEM: " + currentItem.getName());

        if (!currentItem.getClass().equals(Service.class)) {
            Product product = productService.findById(itemId);
            product.setInv(product.getInv() - 1);
            productService.save(product);
        }

        // COMPARE orderItem with elements of order.getOrderItemSet()
        if(!order.getOrderItemSet().contains(orderItem)) {
            // orderItem NOT exists in the orderItemSet
            orderItem.setQuantity(1);
            order.getOrderItemSet().add(orderItem);
        } else {
            // orderItem DOES exist in the orderItemSet
            System.out.println("duplicate item in order");
            OrderItem orderItemInOrder = order.getOrderItemSet()
                    .stream()
                    .filter(o -> o.equals(orderItem))
                    .findFirst().get();
            orderItemInOrder.setQuantity(orderItemInOrder.getQuantity() + 1);
        }
        order.setTotalPrice(order.getTotalPrice() + orderItem.getItem().getPrice());
        return "redirect:/";
    }

    @GetMapping("/changeQuantity")
    public String changeQuantity(@RequestParam int itemId,
                                 @RequestParam("increase") boolean increaseQuantityInOrder,
                                 RedirectAttributes redirectAttributes) {
        int changeAmount = increaseQuantityInOrder? 1:-1;

        Item itemFromRepo = itemService.findById(itemId);
        if (!itemFromRepo.getClass().equals(Service.class)) {
            Product product = productService.findById(itemId);
            if(product.getInv()==0 && increaseQuantityInOrder) {
                redirectAttributes.addFlashAttribute("itemError", itemId);
                return "redirect:/api/v1/user/sales";
            }

            product.setInv(product.getInv() - changeAmount);
            productService.save(product);
        }

        OrderItem currentItem = order.getOrderItemSet()
                .stream()
                .filter(o -> o.getItem().getId() == (long)itemId)
                .findFirst().get();

        currentItem.setQuantity(currentItem.getQuantity() + changeAmount);
        order.setTotalPrice(order.getTotalPrice() + changeAmount * currentItem.getItem().getPrice());
        order.setOrderItemSet(order.getOrderItemSet().stream().filter(orderItem -> orderItem.getQuantity()!=0).collect(Collectors.toSet()));
        return "redirect:/api/v1/user/sales";
    }

    @GetMapping("/orderList")
    public String getOrderList(Model model,
                               @Param("orderKeyword") String orderKeyword,
                               @ModelAttribute String filterCriteria) {
            List<Order> orderFromRepos = orderService.findAllBy(filterCriteria, orderKeyword);

            model.addAttribute("orderFromRepos", orderFromRepos);
            model.addAttribute("orderKeyword", orderKeyword);
            return "menu pages/orders";

    }

    @Transactional
    @PostMapping("/saveOrder")
    public String saveOrder(
            @ModelAttribute("customer") int customerId,
            @ModelAttribute("paymentMethod") String paymentMethod,
            RedirectAttributes redirectAttributes
    ) {

        if(order.getOrderItemSet().isEmpty()) {
            redirectAttributes.addFlashAttribute("itemError", "Please add item to order before saving.");
            return "redirect:/api/v1/user/sales";
        }

        currentCustomer = customerService.findById(customerId);

        order.setCustomer(currentCustomer);
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));

        SaveCurrentOrderAndReport();
        ResetOrderSession();
        return "redirect:/";
    }

    @GetMapping("/addCustomer")
    public String getNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        model.addAttribute("action", "add");
        return "customer_form";
    }

    @PostMapping("/processCustomer")
    public String processCustomer(@ModelAttribute Customer customer) {
        customerService.save(customer);
        return "redirect:/api/v1/user/sales";
    }
//
    @GetMapping("/manageCustomers")
    public String getManageCustomersPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "menu pages/customers";
    }

    @GetMapping("/updateCustomer")
    public String getUpdateCustomerPage(@RequestParam int customerId,
                                    Model model) {

        Customer updateCustomer = customerService.findById(customerId);
        model.addAttribute("customer", updateCustomer);
        model.addAttribute("action", "update");
        return "customer_form";
    }
    @PostMapping("/updateCustomer")
    public String updateCustomerProcess(@RequestParam int customerId,
                                    @ModelAttribute("customer") Customer updateCustomer) {

        customerService.deleteById(customerId);
        customerService.save(updateCustomer);
        return "redirect:/api/v1/user/manageCustomers";
    }

    @GetMapping("/deleteCustomer")
    public String deleteCustomer(@RequestParam int customerId) {
        customerService.deleteById(customerId);
        return "redirect:/api/v1/user/manageCustomers";
    }

    void SaveCurrentOrderAndReport() {
        Order myOrder = new Order();
        myOrder.setPaymentMethod(order.getPaymentMethod());
        myOrder.setCustomer(order.getCustomer());
        order.getOrderItemSet().forEach(myOrder::addOrderItem);
        myOrder.setTotalPrice(order.getTotalPrice());
        orderService.save(myOrder);

        Report report = new Report(
                myOrder,
                appUser.getUsername(),
                order.getCustomer(),
                order.getTotalPrice());
        reportService.save(report);
    }
    void ResetOrderSession() {
        order.getOrderItemSet().clear();
        order.setPaymentMethod(null);
        order.setCustomer(null);
        order.setTotalPrice(0);
    }
}
