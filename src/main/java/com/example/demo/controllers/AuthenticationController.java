package com.example.demo.controllers;

import com.example.demo.dto.SigninRequest;
import lombok.RequiredArgsConstructor;
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

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/signInPage")
    public String getSignInPage(Model model) {
        model.addAttribute("signInRequest", new SigninRequest());
        return "sign_in";
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/signInProcess")
    public String signIn(@ModelAttribute("signInRequest") SigninRequest signinRequest, BindingResult bindingResult,
                          HttpServletRequest httpServletRequest, Model model) {
        if (bindingResult.hasErrors()) {
            return "sign_in";
        }

//        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signinRequest);
//        String token = jwtAuthenticationResponse.getToken();
//        httpServletRequest.getSession().setAttribute("token", token);
//
//        User user1 = userRepository.findByEmail(jwtService.extractUserName(token)).get();
//        user.setFirstName(user1.getFirstName());
//        user.setLastName(user1.getLastName());
//        user.setEmail(user1.getEmail());
        return "redirect:/inventory";
    }
}
