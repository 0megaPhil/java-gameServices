package com.firmys.gameservices.inventory.config;

import com.firmys.gameservices.common.config.CommonConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({CommonConfig.class, SpringSecurityConfiguration.class})
public class InventoryConfig {}
