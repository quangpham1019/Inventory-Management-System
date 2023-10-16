package com.example.demo.service.security.Impl;

import com.example.demo.service.security.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

// Generate TOKEN, validate TOKEN
@Service
public class JWTServiceImpl implements JWTService {

    // Generate JSON Web Token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSigninKey() {
        byte[] key = Decoders.BASE64.decode("y93J5/Qq1xmlZvBa+TM6r5pZY9cohnFvlCW2ongwFko=");
        return Keys.hmacShaKeyFor(key);
    }

    // Extract username (email) from a TOKEN
    public String extractUserName(String token) {
        // return the EMAIL stored in a particular token
        return extractClaim(token, Claims::getSubject);
    }

    // extract CLAIMS from the TOKEN
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    // return all CLAIMS from a token
    private Claims extractAllClaims(String token) {
            return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }

    // Check if TOKEN is VALID
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Check if TOKEN is EXPIRED
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
