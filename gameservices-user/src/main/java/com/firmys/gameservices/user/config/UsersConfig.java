package com.firmys.gameservices.user.config;

import com.firmys.gameservices.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
public class UsersConfig {}
