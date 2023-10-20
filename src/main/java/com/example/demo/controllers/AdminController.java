package com.example.demo.controllers;

import com.example.demo.repositories.ReportRepository;
import com.example.demo.domain.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Access;
import java.util.Set;
import java.util.stream.Collectors;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private ReportRepository reportRepository;
    @GetMapping("/")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello admin");
    }

    @GetMapping("/session")
    public String getAdminSession() {
        return "/admin/index";
    }

    @GetMapping("/report")
    public String getReportPage(Model model) {
        Set<Report> reports = reportRepository.findAll().stream().collect(Collectors.toSet());
        model.addAttribute("reports", reports);
        return "report";
    }
    @GetMapping("/manageEmployees")
    public String getManageEmployeesPage() {
        return "manageEmployees";
    }
}
