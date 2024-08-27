package com.example.demo.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/signInPage")
    public String getSignInPage() {
        return "sign_in";
    }
}
