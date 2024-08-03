package com.firmys.gameservices.common.security;

import static com.firmys.gameservices.common.CommonConstants.PROFILE_SERVICE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.Ssl;
import org.springframework.context.annotation.*;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

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
        "/skill*/**",
        "/race*/**",
        "/stat*/**",
        "/transaction*/**",
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
        "/swagger-ui/**",
        "/graphql*"
      };

  private CorsConfigurationSource createCorsConfigSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.addAllowedOrigin("http://localhost:4200");
    config.addAllowedMethod("OPTIONS");
    config.addAllowedMethod("GET");
    config.addAllowedMethod("PUT");
    config.addAllowedMethod("POST");
    config.addAllowedMethod("DELETE");
    config.addAllowedHeader("Access-Control-Allow-Origin");
    config.addAllowedHeader("authorization");
    config.addAllowedHeader("Content-Type");
    config.addAllowedHeader("credential");
    config.addAllowedHeader("X-XSRF-TOKEN");
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  @Profile(PROFILE_SERVICE)
  public SecurityWebFilterChain configure(ServerHttpSecurity http) {
    return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
        .cors(cors -> cors.configurationSource(createCorsConfigSource()))
        .x509(Customizer.withDefaults())
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .authorizeExchange(
            auth -> auth.pathMatchers(permittedUrl).permitAll().pathMatchers("/**").authenticated())
        .build();
  }

  @Bean
  @Primary
  public ServerProperties serverProperties(
      @Value("${server.ssl.key-store}") String keyStoreStr,
      @Value("${server.ssl.key-store-password}") String keyStorePassword) {
    Ssl ssl = new Ssl();
    ssl.setKeyStore(keyStoreStr);
    ssl.setKeyStorePassword(keyStorePassword);
    ServerProperties serverProperties = new ServerProperties();
    serverProperties.setSsl(ssl);
    return serverProperties;
  }
}
