package com.firmys.gameservices.users.config;

import com.firmys.gameservices.common.config.CommonConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonConfig.class, SpringSecurityConfiguration.class})
public class UsersConfig {}
