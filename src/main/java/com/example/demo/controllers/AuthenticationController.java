package com.example.demo.controllers;

import com.example.demo.domain.*;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ReportRepository;
import com.example.demo.repositories.ServiceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import com.example.demo.service.security.AuthenticationService;
import com.example.demo.service.security.JWTService;
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
    private ReportRepository reportRepository;
    @Autowired
    private User user;
    @Autowired
    private Order order;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepository;
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

        User user1 = userRepository.findByEmail(jwtService.extractUserName(token.toString())).get();
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        httpServletRequest.getSession().setAttribute("token", token);
        model.addAttribute("token", token);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('USER')")
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

        Service service = new Service();
        serviceRepository.save(service);
        Set<OrderItem> orderItemSet = new HashSet<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(productService.findById(2));
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setItem(productService.findById(1));
//        orderItem.setOrder(order);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setItem(service);
        orderItemSet.add(orderItem);
        orderItemSet.add(orderItem1);
        orderItemSet.add(orderItem2);
        order.setOrderItemSet(orderItemSet);
        System.out.println(order);

        return "redirect:/";
    }

    @Transactional
    @GetMapping("/saveOrder")
    public String saveOrder() {
        Order myOrder = new Order();
        myOrder.setOrderNumber(order.getOrderNumber());
        myOrder.setCustomer(order.getCustomer());
        order.getOrderItemSet().forEach(o -> {
            myOrder.addOrderItem(o);
            order.setTotalPrice(order.getTotalPrice() + o.getItem().getPrice()*o.getQuantity());
        });
        myOrder.setTotalPrice(order.getTotalPrice());
        orderRepository.save(myOrder);

        // log user activity to report
        Report report = new Report();
        report.setOrder(myOrder);
        report.setUser(user.getFirstName() + " " + user.getLastName());
        report.setCustomer(order.getCustomer());
        report.setPrice(order.getTotalPrice());

        reportRepository.save(report);

        System.out.println("saving order");
        // reset order("session")
        order.setOrderItemSet(new HashSet<>());
        order.setOrderNumber(null);
        order.setCustomer(null);
        order.setTotalPrice(0);



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
