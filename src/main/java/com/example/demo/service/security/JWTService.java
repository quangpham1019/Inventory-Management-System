package com.example.demo.service.security;

import com.example.demo.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JWTService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpired(String token);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
