package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.domain.Report;
import com.example.demo.security.AppUser;
import com.example.demo.security.AppUserService;
import com.example.demo.security.LoginProvider;
import com.example.demo.security.db.UserEntity;
import com.example.demo.security.db.UserEntityRepository;
import com.example.demo.service.JcsUserService;
import com.example.demo.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserEntityRepository userEntityRepository;
    private final AppUserService appUserService;
    private ReportService reportService;
    private JcsUserService jcsUserService;

    @Autowired
    public AdminController(ReportService reportService, JcsUserService jcsUserService, UserEntityRepository userEntityRepository, AppUserService appUserService) {
        this.reportService = reportService;
        this.jcsUserService = jcsUserService;
        this.userEntityRepository = userEntityRepository;
        this.appUserService = appUserService;
    }


    @GetMapping("/report")
    public String getReportPage(Model model,
                                @Param("reportKeyword") String reportKeyword,
                                @ModelAttribute String filterCriteria) {
        List<Report> reports = reportService.findAllBy(filterCriteria, reportKeyword);

        model.addAttribute("reportKeyword", reportKeyword);
        model.addAttribute("reports", reports);
        return "menu pages/reports";
    }
    @GetMapping("/manageUsers")
    public String getManageUsersPage(Model model) {
        List<UserEntity> users = (List<UserEntity>)userEntityRepository.findAll();
        model.addAttribute("users", users);
        return "menu pages/users";
    }

    @GetMapping("/addUser")
    public String getNewUserForm(Model model,
                                 @ModelAttribute("error") Exception e) {
        model.addAttribute("updateUser", new UserEntity());
        model.addAttribute("oldUsername", "");
        model.addAttribute("action", "add");
        model.addAttribute("error", e);
        return "user_form";
    }

    @PostMapping("/processUser")
    public String processNewEmployee(@ModelAttribute(name = "user") UserEntity newUser) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var userToAdd = AppUser.builder()
                .username(newUser.getUsername())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode(newUser.getPassword()))
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .build();

        appUserService.createUser(userToAdd);
        return "confirmation/user_add_success";
    }

    @GetMapping("/updateUser")
    public String getUpdateUser(@RequestParam String username, Model model) {

        UserEntity user = userEntityRepository.findById(username).orElse(null);
        model.addAttribute("updateUser", user);
        model.addAttribute("oldUsername", user.getUsername());
        model.addAttribute("action", "update");
        return "user_form";
    }

    @PostMapping("/updateUser")
    public String updateUserProcess(@RequestParam String USERNAME,
                                    @ModelAttribute("updateUser") UserEntity updateUser,
                                    Model model,
                                    Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "update");
            return "user_form";
        }

        updateUser.setUsername(USERNAME);
        appUserService.updateUser(updateUser);

        return "redirect:/api/v1/admin/manageUsers";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String username) {
        appUserService.deleteUser(username);
        return "redirect:/api/v1/admin/manageUsers";
    }
}
