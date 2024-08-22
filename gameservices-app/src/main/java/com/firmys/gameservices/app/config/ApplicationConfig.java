package com.firmys.gameservices.app.config;

import static com.firmys.gameservices.common.CommonConstants.PACKAGE_GENERATED;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.config.WebConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import com.firmys.gameservices.service.GameServiceClient;
import com.firmys.gameservices.service.config.ConversionConfig;
import com.firmys.gameservices.service.error.GraphQLExceptionController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  GameServiceClient.class,
  WebConfig.class,
  ConversionConfig.class,
  SpringSecurityConfiguration.class,
  GraphQLExceptionController.class
})
@ComponentScan(basePackages = {PACKAGE_GENERATED})
@EnableConfigurationProperties(CommonProperties.class)
public class ApplicationConfig {}
