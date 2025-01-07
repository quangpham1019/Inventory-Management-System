package com.example.demo.Controller;

import com.example.demo.Domain.*;
import com.example.demo.Security.AppUser;
import com.example.demo.Service.Data.Interface.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SalesController {

    private final CustomerService customerService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ItemService itemService;
    private final ReportService reportService;

    private final Order order;
    private static Customer currentCustomer;
    private AppUser appUser;

    @ModelAttribute
    AppUser appUser(@AuthenticationPrincipal AppUser appUser) {
        this.appUser = appUser;
        return appUser;
    }

    @GetMapping
    public String getSalesPage(Model model,
                               @Param("customerKeyword") String customerKeyword,
                               @ModelAttribute String itemError) {
        List<Customer> customerList = customerService.listAllByKeyword(customerKeyword);
        if (!itemError.isBlank()) {
            // string length is less than 12, invalidate int values from range of itemId
            if (itemError.length()<12) {
                Product product = productService.findById((long) Integer.parseInt(itemError));
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
    public String addItemToOrder(@RequestParam int itemId,
                                 RedirectAttributes redirectAttributes,
                                 HttpServletRequest request) {

        Item currentItem = itemService.findById((long) itemId);

        System.out.println(request.getRequestURL().toString() + "?" + request.getQueryString());
        System.out.println(itemId);
        if (!itemService.adjustItemQuantityInInventory(currentItem, -1)) {
            redirectAttributes.addFlashAttribute("itemError", itemId);
            return "redirect:/";
        }
        orderService.addItemToOrder(order, currentItem);

        return "redirect:/";
    }

    @GetMapping("/adjustQuantity")
    public String adjustQuantity(@RequestParam int itemId,
                                 @RequestParam("increase") boolean quantityInOrderIncreasing,
                                 RedirectAttributes redirectAttributes) {
        
        Item itemFromRepo = itemService.findById((long) itemId);
        int changeOfQuantityInOrder = quantityInOrderIncreasing? 1:-1;

        if (!itemService.adjustItemQuantityInInventory(itemFromRepo, -changeOfQuantityInOrder)) {
            redirectAttributes.addFlashAttribute("itemError", itemId);
            return "redirect:/api/v1/sales";
        }

        orderService.adjustItemQuantityInOrder(order, itemId, changeOfQuantityInOrder);

        return "redirect:/api/v1/sales";
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
            return "redirect:/api/v1/sales";
        }

        currentCustomer = customerService.findById((long) customerId);

        order.setCustomer(currentCustomer);
        order.setPaymentMethod(PaymentMethod.valueOf(paymentMethod));

        saveCurrentOrderAndReport();
        resetOrderSession();
        return "redirect:/";
    }

    private void saveCurrentOrderAndReport() {
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
    private void resetOrderSession() {
        order.getOrderItemSet().clear();
        order.setPaymentMethod(null);
        order.setCustomer(null);
        order.setTotalPrice(0);
    }
}
