package com.example.demo.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class CustomExceptionHandler {



    @ExceptionHandler({ AccessDeniedException.class })
    public String handleAccessDeniedException(AccessDeniedException exception) {
        System.out.println(exception.getMessage());
        return "access_denied";
    }

}
