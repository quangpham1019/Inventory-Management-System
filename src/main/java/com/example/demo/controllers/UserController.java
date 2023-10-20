package com.example.demo.controllers;

import com.example.demo.domain.Item;
import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.User;
import com.example.demo.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private PartService partService;
    @Autowired
    private Order order;
    @Autowired
    private User user;
//    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello user");
    }
    @GetMapping("/sales")
    public String getSalesPage(Model model) {

//        Set<Item> itemSet = new HashSet<>();
//        order.getOrderItemSet().forEach(i ->
//                itemSet.add(i.getItem())
//        );
        model.addAttribute("order", order);

        return "sales";
    }

    @GetMapping("/addItemToCartGet")
    public String getAddCartItemPage(Model model) {
        model.addAttribute("cartItem", user);
        return "addingCartItem";
    }

    @PostMapping("/addItemToCart")
    public String addCartItem(@ModelAttribute(name = "cartItem") User cartItem) {

        user.setLastName(cartItem.getLastName());
        user.setFirstName(cartItem.getFirstName());
        return "redirect:/";
    }

    @GetMapping("/addItemToOrder")
    public String addItemToOrder(@RequestParam("partID") int partId) {

        // create new OrderItem orderItem
        // set Item of orderItem to part retrieved from partService.findById(partId)
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(partService.findById(partId));
        System.out.println("ADDING ITEM: " + orderItem.getItem().getName());

        // COMPARE orderItem with elements of order.getOrderItemSet()
        if(!order.getOrderItemSet().contains(orderItem)) {
            // orderItem NOT exists in the orderItemSet
            orderItem.setQuantity(1);
            order.getOrderItemSet().add(orderItem);
        } else {
            // orderItem DOES exists in the orderItemSet
            System.out.println("duplicate item in order");
            OrderItem currentItem = order.getOrderItemSet()
                    .stream()
                    .filter(o -> o.equals(orderItem))
                    .findFirst().get();
            currentItem.setQuantity(currentItem.getQuantity() + 1);
        }

//        // VERIFICATION
//        order.getOrderItemSet().forEach(o ->
//                System.out.println("QUANTITY OF ITEM " + o.getItem().getName() + ": " + o.getQuantity())
//        );
        return "redirect:/";
    }
}
