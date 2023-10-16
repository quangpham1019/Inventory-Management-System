package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.service.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class AppController {

    @Autowired
    private User user;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserRepository userRepo;

    @GetMapping("")
    public String viewHomePage(HttpServletRequest httpServletRequest, Model model) {

        Object jwt = httpServletRequest.getSession().getAttribute("token");
        if (jwt != null) {
            model.addAttribute("username", jwtService.extractUserName(jwt.toString()));
        }
        user.setEmail("USER_EMAIL");
        List<String> sessionAttr = Collections.list(httpServletRequest.getSession().getAttributeNames());
        List<String> sessionAttrValue = new ArrayList<>();
        for (String attr : sessionAttr) {
            sessionAttrValue.add(httpServletRequest.getSession().getAttribute(attr).toString());
        }

        System.out.println(user.getEmail());
        model.addAttribute("sessionAttr", sessionAttr);
        model.addAttribute("sessionAttrValue", sessionAttrValue);
        System.out.println("REDIRECTING TO index");
        return "index";
    }

//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//
//        return "signup_form";
//    }

//    @PostMapping("/process_register")
//    public String processRegister(User user) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//
//        userRepo.save(user);
//
//        return "register_success";
//    }
}