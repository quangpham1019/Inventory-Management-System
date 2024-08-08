package com.example.demo.security;

import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Value
public class UserCreatorService {

    AppUserService appUserService;
    PasswordEncoder passwordEncoder;

    AdminConfig adminConfig;

    @PostConstruct
    private void createHardcodedUsers() {
        var user = AppUser.builder()
                .username("jcsuser@gmail.com")
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode("jcsuser"))
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .build();

        var admin = AppUser.builder()
                .username("jcsadmin@gmail.com")
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode("jcsadmin"))
                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
                .build();

        appUserService.createUser(admin);
        appUserService.createUser(user);

    }
}
