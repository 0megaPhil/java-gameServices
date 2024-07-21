package com.firmys.gameservices.gateway.config;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringSecurityConfiguration.class})
@EnableConfigurationProperties(CommonProperties.class)
public class GatewayRouteConfig {}
