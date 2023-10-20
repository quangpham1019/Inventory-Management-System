package com.example.demo.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello admin");
    }

    @GetMapping("/session")
    public String getAdminSession() {
        return "/admin/index";
    }

    @GetMapping("/report")
    public String getReportPage() {
        return "report";
    }
    @GetMapping("/manageEmployees")
    public String getManageEmployeesPage() {
        return "manageEmployees";
    }
}
