package com.firmys.gameservices.world.config;

import com.firmys.gameservices.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
public class WorldConfig {}
