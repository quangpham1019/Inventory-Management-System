package com.example.demo.Controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({ SQLIntegrityConstraintViolationException.class})
    public String handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "redirect:/api/v1/user/addUser";
    }
    @ExceptionHandler({ AccessDeniedException.class })
    public String handleAccessDeniedException(AccessDeniedException exception) {
        System.out.println(exception.getMessage());
        return "error/error_access_denied";
    }
}
