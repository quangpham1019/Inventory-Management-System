package com.example.demo.Controller;

import com.example.demo.Security.AppUser;
import com.example.demo.Security.UserDetailsManagerImpl;
import com.example.demo.Security.LoginProvider;
import com.example.demo.Domain.UserEntity;
import com.example.demo.Service.Authentication.Interface.AuthenticationHelperService;
import com.example.demo.Service.Data.Interface.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Refactor UserController into ReportController, UserController
@PreAuthorize("hasAuthority('ADMIN')")
@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDetailsManagerImpl userDetailsManagerImpl;
    private final AuthenticationHelperService authenticationHelperService;

    @GetMapping("/manageUsers")
    public String getManageUsersPage(Model model) {

        model.addAttribute("users", userEntityService.findAll());
        return "menu pages/users";
    }

    @GetMapping("/addUser")
    public String getAddUserForm(Model model,
                                 @ModelAttribute("error") Exception e) {

        model.addAttribute("updateUser", new UserEntity());
        model.addAttribute("oldUsername", "");
        model.addAttribute("action", "add");
        model.addAttribute("error", e);
        return "form/user_form";
    }

    @PostMapping("/addUser")
    public String addNewUser(@ModelAttribute(name = "user") UserEntity newUser) {

        AppUser userToAdd = authenticationHelperService.mapUserEntityToAppUser(newUser);
        userDetailsManagerImpl.createUser(userToAdd);
        return "confirmation/user_add_success";
    }

    @GetMapping("/updateUser")
    public String getUpdateUserForm(@RequestParam String username, Model model) {

        UserEntity user = userEntityService.findById(username);

        model.addAttribute("updateUser", user);
        model.addAttribute("oldUsername", user.getUsername());
        model.addAttribute("action", "update");
        return "form/user_form";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam String USERNAME,
                                    @ModelAttribute("updateUser") UserEntity updateUser,
                                    Model model,
                                    Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("action", "update");
            return "form/user_form";
        }

        updateUser.setUsername(USERNAME);
        userDetailsManagerImpl.updateUser(updateUser);

        return "redirect:/api/v1/user/manageUsers";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam String username) {
        userDetailsManagerImpl.deleteUser(username);
        return "redirect:/api/v1/user/manageUsers";
    }
}
