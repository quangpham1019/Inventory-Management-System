package com.example.demo.Service.Data.Implementation.UsingCRUDRepository;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Repository.CRUDRepository.UserEntityRepository;
import com.example.demo.Service.Data.Interface.UserEntityService;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceUsingCRUDRepository
        extends CommonServiceUsingCRUDRepository<UserEntity, String>
        implements UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public UserEntityServiceUsingCRUDRepository(UserEntityRepository userEntityRepository) {
        super(userEntityRepository);
        this.userEntityRepository = userEntityRepository;
    }
}
