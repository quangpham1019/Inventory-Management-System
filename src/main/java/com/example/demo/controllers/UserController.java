package com.example.demo.controllers;

import com.example.demo.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private User user;
//    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello user");
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
}
