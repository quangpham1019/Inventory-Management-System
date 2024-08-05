package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JcsUserServiceImpl implements JcsUserService{

    private UserRepository userRepository;

    public JcsUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }
    @Override
    public boolean hasAdminRole() {
        return userRepository.numAdminRole()!=0;
    }
    @Override
    public boolean hasUserRole() {
        return userRepository.numUserRole()!=0;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void deleteById(int userId) {
        userRepository.deleteById((long) userId);
    }

    @Override
    public User findById(int userId) {
        Optional<User> user = userRepository.findById((long) userId);

        return user.orElseThrow(() -> new IllegalArgumentException("Could not find user with id: " + userId));
    }
}
