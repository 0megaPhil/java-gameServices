package com.firmys.gameservices.app.config;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.common.config.WebClientConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({SpringSecurityConfiguration.class, GatewayClient.class, WebClientConfig.class})
@EnableConfigurationProperties(CommonProperties.class)
public class RouteConfig {}
