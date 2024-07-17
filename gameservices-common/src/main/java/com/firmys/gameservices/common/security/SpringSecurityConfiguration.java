package com.firmys.gameservices.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SpringSecurityConfiguration {

  private static final String[] permittedUrl =
      new String[] {
        "/inventor*/**",
        "/character*/**",
        "/world**",
        "/item*/**",
        "/currenc*/**",
        "*api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "*swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/v3/api-docs.yml/**",
        "/v3/api-docs.yaml/**",
        "/swagger-ui/**"
      };

  @Bean
  public SecurityWebFilterChain configure(ServerHttpSecurity http) {
    return http.csrf(Customizer.withDefaults())
        .x509(Customizer.withDefaults())
        .authorizeExchange(
            auth -> auth.pathMatchers(permittedUrl).permitAll().pathMatchers("/**").authenticated())
        .build();
  }
}
