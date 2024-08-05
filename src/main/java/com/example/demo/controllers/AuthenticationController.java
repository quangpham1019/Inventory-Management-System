package com.example.demo.controllers;

import com.example.demo.domain.*;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ReportRepository;
import com.example.demo.repositories.ServiceRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.ProductService;
import com.example.demo.service.security.AuthenticationService;
import com.example.demo.service.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;


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

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/signInPage")
    public String getSignInPage(Model model) {
        model.addAttribute("signInRequest", new SigninRequest());
        return "sign_in_form";
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/signInProcess")
    public String signIn(@ModelAttribute("signInRequest") SigninRequest signinRequest, BindingResult bindingResult,
                          HttpServletRequest httpServletRequest, Model model) {
        if (bindingResult.hasErrors()) {
            return "sign_in_form";
        }

        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signinRequest);
        String token = jwtAuthenticationResponse.getToken();
        httpServletRequest.getSession().setAttribute("token", token);

        User user1 = userRepository.findByEmail(jwtService.extractUserName(token)).get();
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setEmail(user1.getEmail());
        return "redirect:/";
    }



    @GetMapping("/signOutProcess")
    public String destroySession(HttpServletRequest request) {
        System.out.println("DESTROYING SESSION");
        request.getSession().invalidate();
        return "redirect:/";
    }

    // TODO: integrate refreshToken with JwtAuthenticationFilter
    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
    }
}
