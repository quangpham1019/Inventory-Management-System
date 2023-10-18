package com.example.demo.controllers;

import com.example.demo.domain.Order;
import com.example.demo.domain.OrderItem;
import com.example.demo.domain.Product;
import com.example.demo.domain.User;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import com.example.demo.service.security.AuthenticationService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private Order order;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;
    private final AuthenticationService authenticationService;

//    @PostMapping("/signup")
//    public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
//        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
//    }

//    @PostMapping("/signin")
//    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest) {
//        return ResponseEntity.ok(authenticationService.signin(signinRequest));
//    }
    @PreAuthorize("permitAll()")
    @GetMapping("/signin2get")
    public String getSignInPage(Model model) {
        SigninRequest signinRequest = new SigninRequest();
        model.addAttribute("signInRequest", signinRequest);
        return "sign_in";
    }
    @PostMapping("/signin2")
    public String signin2(@ModelAttribute("signInRequest") SigninRequest signinRequest, BindingResult bindingResult, Model model,
                          HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "sign_in";
        }



        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signinRequest);
        String token = jwtAuthenticationResponse.getToken();

        httpServletRequest.getSession().setAttribute("token", token);
        model.addAttribute("token", token);
        return "redirect:/";
    }

    @GetMapping("/signin2/session")
    public String testSession(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        model.addAttribute("tokenFromSession", httpServletRequest.getSession().getAttribute("token"));

        return "test_session";
    }

    @GetMapping("/testSession")
    public String testSession() {

        return "test_session";
    }
    @GetMapping("/addItemToOrder")
    public String addItemToOrder() {

        Set<OrderItem> orderItemSet = new HashSet<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(orderRepository.getById(order.getId()));
        orderItem.setOrderItemGeneral(productService.findById(2));
        orderItemSet.add(orderItem);
        order.setOrderItemSet(orderItemSet);
        System.out.println(order);

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/saveOrder")
    public String saveOrder() {
        Order myOrder = new Order();
        myOrder.setOrderNumber(order.getOrderNumber());
        order.getOrderItemSet().forEach(item -> myOrder.add(item));
        orderRepository.save(myOrder);
        System.out.println("saving order");
        return "redirect:/";
    }

    @GetMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        System.out.println("DESTROYING SESSION");
        request.getSession().invalidate();
        return "redirect:/";
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
