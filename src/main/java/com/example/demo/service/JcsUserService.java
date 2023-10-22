package com.example.demo.service;

import com.example.demo.domain.User;

import java.util.List;

public interface JcsUserService {

    void save(User user);
    boolean hasAdminRole();
    boolean hasUserRole();
    List<User> findAll();
    void deleteById(int userId);
    User findById(int userId);
}
