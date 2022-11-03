package com.firmys.gameservices.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SpringSecurityConfiguration {

    // TODO - Configure real security options
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .csrf()
                .disable()
                .headers()
                .frameOptions().disable()
                .cache().disable()
                .and()
                .authorizeExchange()
                .pathMatchers(
                        /*
                         * API
                         */
                        "/inventor*/**", "/character*/**", "/world**", "/item*/**", "/currenc*/**",
                        /*
                         * Swagger UI V3 OpenApi
                         */
                        "/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/v3/api-docs.yml/**",
                        "/v3/api-docs.yaml/**",
                        "/swagger-ui/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .build();
    }

}
