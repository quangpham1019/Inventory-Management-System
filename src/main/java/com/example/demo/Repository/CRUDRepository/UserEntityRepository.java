package com.example.demo.Repository.CRUDRepository;

import com.example.demo.Domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserEntityRepository extends CrudRepository<UserEntity, String> {
}
