package com.firmys.gameservices.inventory.config;

import com.firmys.gameservices.service.config.ServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
public class InventoryConfig {}
