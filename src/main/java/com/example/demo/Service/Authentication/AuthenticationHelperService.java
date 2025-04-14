package com.example.demo.Service.Authentication;

import com.example.demo.Domain.UserEntity;
import com.example.demo.Security.AppUser;

public interface AuthenticationHelperService {
    AppUser mapUserEntityToAppUser(UserEntity userEntity);
    void createHardcodedUsers();
}
