package com.firmys.gameservices.app.config;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.common.config.ConversionConfig;
import com.firmys.gameservices.common.config.WebClientConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import graphql.scalars.ExtendedScalars;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
@Import({
  SpringSecurityConfiguration.class,
  GatewayClient.class,
  ConversionConfig.class,
  WebClientConfig.class
})
@EnableConfigurationProperties(CommonProperties.class)
public class ApplicationConfig {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder -> wiringBuilder.scalar(ExtendedScalars.Json);
  }
}
