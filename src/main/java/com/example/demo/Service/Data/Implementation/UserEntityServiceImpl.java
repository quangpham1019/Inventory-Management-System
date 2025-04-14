package com.example.demo.Service.Data.Implementation;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl
        extends CommonServiceImpl<UserEntity, String>
        implements com.example.demo.Service.Data.UserEntityService {

    private final UserEntityRepository userEntityRepository;

    public UserEntityServiceImpl(UserEntityRepository userEntityRepository) {
        super(userEntityRepository);
        this.userEntityRepository = userEntityRepository;
    }
}
