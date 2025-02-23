package com.example.demo.Security;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Log4j2
public class SecurityConfiguration {


    @Bean
    @Order(0)
    SecurityFilterChain resources(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/api/v1/auth/signInPage", "/images/**", "/css/**.css", "/**.js")
                .authorizeHttpRequests(c -> c.anyRequest().permitAll())
                .securityContext(c -> c.disable())
                .sessionManagement(c -> c.disable())
                .requestCache(c -> c.disable())
                .build();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2LoginHandler,
                                            OAuth2UserService<OidcUserRequest, OidcUser> oidcLoginHandler) throws Exception {
        return http
                .formLogin(c -> c.loginPage("/api/v1/auth/signInPage")
                        .loginProcessingUrl("/api/v1/auth/signInProcess")
                        .usernameParameter("user")
                        .passwordParameter("pass")
                        .defaultSuccessUrl("/inventory")
                )
                .logout(c -> c.logoutSuccessUrl("/?logout"))
                .oauth2Login(oc -> oc
                        .loginPage("/api/v1/auth/signInPage")
                        .defaultSuccessUrl("/inventory")
                        .userInfoEndpoint(ui -> ui
                                .userService(oauth2LoginHandler)
                                .oidcUserService(oidcLoginHandler)))
                .authorizeHttpRequests(c -> c
                        .requestMatchers(EndpointRequest.to("info", "health", "prometheus")).permitAll()
                        .requestMatchers(EndpointRequest.toAnyEndpoint().excluding("info", "health", "prometheus")).hasAuthority("manage")
                        .requestMatchers("/", "/login", "/user/sign-up", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    ApplicationListener<AuthenticationSuccessEvent> successLogger() {
        return event -> {
            log.info("success: {}", event.getAuthentication());
        };
    }
}
