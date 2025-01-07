package com.example.demo.Service.Authentication.Implementation;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Security.AdminConfig;
import com.example.demo.Security.AppUser;
import com.example.demo.Security.UserDetailsManagerImpl;
import com.example.demo.Security.LoginProvider;
import com.example.demo.Service.Authentication.Interface.AuthenticationHelperService;
import jakarta.annotation.PostConstruct;
import lombok.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Value
public class AuthenticationHelperServiceImpl implements AuthenticationHelperService {

    UserDetailsManagerImpl userDetailsManagerImpl;
    PasswordEncoder passwordEncoder;

    AdminConfig adminConfig;

    @PostConstruct
    @Override
    public void createHardcodedUsers() {
        var user = AppUser.builder()
                .username("jcsuser@gmail.com")
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode("jcsuser"))
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .build();

        var admin = AppUser.builder()
                .username("jcsadmin@gmail.com")
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode(adminConfig.adminPassword))
                .authorities(List.of(new SimpleGrantedAuthority("ADMIN")))
                .build();

        userDetailsManagerImpl.createUser(admin);
        userDetailsManagerImpl.createUser(user);
    }

    @Override
    public AppUser mapUserEntityToAppUser(UserEntity userEntity) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return AppUser.builder()
                .username(userEntity.getUsername())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .provider(LoginProvider.APP)
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .authorities(List.of(new SimpleGrantedAuthority("USER")))
                .build();
    }
}
