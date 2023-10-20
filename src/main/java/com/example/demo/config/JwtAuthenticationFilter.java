package com.example.demo.config;

import com.example.demo.service.security.JWTService;
import com.example.demo.service.security.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // jwt - store the web token
        final String jwt;
        // userEmail - store the email (username)
        final String userEmail;

//        Object tokenFromSession = request.getSession().getAttribute("token");
//        // Get the AUTHORIZATION HEADER from request
//        final String authHeader = request.getHeader("Authorization");

//        if (StringUtils.isEmpty(authHeader) || !org.apache.commons.lang3.StringUtils.startsWith(authHeader, "Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // if authHeader is not empty || authHeader starts with "Bearer "
//        // assign to jwt the substring of authHeader, starting from the 7th index
//        jwt = authHeader.substring(7);
        if (request.getSession().getAttribute("token") == null) {
            filterChain.doFilter(request, response);
            return;
        }
//        System.out.println("TOKEN PASS NULL CHECK");

        jwt = request.getSession().getAttribute("token").toString();
//        System.out.println(jwt);

        try {
            // Extract the username (email) from the token
            userEmail = jwtService.extractUserName(jwt);
//            System.out.println("TOKEN PASS VALIDITY CHECK");
        } catch (ExpiredJwtException e) {
            System.out.println(e);
            request.getSession().invalidate();
            response.sendRedirect("/");
            return;
        }
        // TODO: IMPLEMENT HANDLING OF EXPIRED TOKEN



        // if username (email) is not empty && securityContext is null
        // SET SECURITY CONTEXT for SECURITY CONTEXT HOLDER
        if (!StringUtils.isEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

            // IF jwt TOKEN is VALID
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Create a new empty security context
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                // Create a new authentication token using userDetails, credentials (null), and userDetails.getAuthorities()
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // Set details to the authentication token
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication token to the security context
                securityContext.setAuthentication(authenticationToken);

                // Set context for the security context holder
                SecurityContextHolder.setContext(securityContext);
            }
        }

        filterChain.doFilter(request, response);
    }
}
