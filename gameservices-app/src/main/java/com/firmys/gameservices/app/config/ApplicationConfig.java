package com.firmys.gameservices.app.config;

import static com.firmys.gameservices.common.CommonConstants.PACKAGE_GENERATED;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.CommonQueryService;
import com.firmys.gameservices.common.ServiceClient;
import com.firmys.gameservices.common.config.ConversionConfig;
import com.firmys.gameservices.common.config.WebClientConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
  SpringSecurityConfiguration.class,
  ServiceClient.class,
  ConversionConfig.class,
  WebClientConfig.class
})
@ComponentScan(
    basePackages = {PACKAGE_GENERATED},
    basePackageClasses = CommonQueryService.class)
@EnableConfigurationProperties(CommonProperties.class)
public class ApplicationConfig {}
