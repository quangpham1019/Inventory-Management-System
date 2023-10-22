package com.example.demo.repositories;

import com.example.demo.domain.RoleType;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
    @Query("SELECT COUNT(u) FROM User u WHERE u.roleType=0")
    int numAdminRole();
    @Query("SELECT COUNT(u) FROM User u WHERE u.roleType=1")
    int numUserRole();
}

