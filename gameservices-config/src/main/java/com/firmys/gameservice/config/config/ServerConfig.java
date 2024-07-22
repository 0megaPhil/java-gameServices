package com.firmys.gameservice.config.config;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.config.WebClientConfig;
import com.firmys.gameservices.common.security.SpringSecurityConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Import({SpringSecurityConfiguration.class, WebClientConfig.class})
@EnableConfigurationProperties(CommonProperties.class)
public class ServerConfig {

  @Bean
  public boolean configBe(ConfigurableEnvironment environment) {
    return true;
  }

  @Bean
  @Primary
  public SecurityWebFilterChain configure(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .x509(Customizer.withDefaults())
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .build();
  }
}
