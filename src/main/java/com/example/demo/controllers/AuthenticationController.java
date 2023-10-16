package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.RefreshTokenRequest;
import com.example.demo.dto.SignUpRequest;
import com.example.demo.dto.SigninRequest;
import com.example.demo.service.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

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
                          HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            return "sign_in";
        }

        JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.signin(signinRequest);
        String token = jwtAuthenticationResponse.getToken();

        httpServletRequest.getSession().setAttribute("token", token);
        model.addAttribute("token", token);
        return "login_success";
    }

    @GetMapping("/signin2/session")
    public String testSession(Model model, HttpServletRequest httpServletRequest) {

        model.addAttribute("tokenFromSession", httpServletRequest.getSession().getAttribute("token"));

        return "test_session";
    }

    @PostMapping("/destroy")
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
