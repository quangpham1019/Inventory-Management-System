package com.example.demo.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdminConfig {
    @Value("jcsadmin")
    public String adminPassword;
}
