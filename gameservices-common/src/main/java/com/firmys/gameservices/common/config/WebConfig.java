package com.firmys.gameservices.common.config;

import static reactor.netty.http.client.HttpClient.*;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.CommonProperties.Service;
import com.firmys.gameservices.common.Services;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class WebConfig {

  @Bean
  public WebClient.Builder webClientBuilder(
      WebFluxConfigurer webFluxConfigurer, SslContext sslContext) {
    HttpClient httpClient = create().secure(sslSpec -> sslSpec.sslContext(sslContext));
    ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);
    return WebClient.builder().clientConnector(clientHttpConnector);
  }

  @Bean
  public JavaTimeModule javatimeModule() {
    return new JavaTimeModule();
  }

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(
      List<SimpleModule> modules) {
    return jacksonObjectMapperBuilder ->
        jacksonObjectMapperBuilder
            .featuresToDisable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .featuresToDisable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .featuresToEnable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            .modules(mod -> mod.addAll(modules));
  }

  @Bean
  public Jackson2JsonEncoder jackson2JsonEncoder(ObjectMapper mapper) {
    return new Jackson2JsonEncoder(mapper);
  }

  @Bean
  public Jackson2JsonDecoder jackson2JsonDecoder(ObjectMapper mapper) {
    return new Jackson2JsonDecoder(mapper);
  }

  @Bean
  public WebFluxConfigurer webFluxConfigurer(
      Jackson2JsonEncoder encoder, Jackson2JsonDecoder decoder) {
    return new WebFluxConfigurer() {
      @Override
      public void configureHttpMessageCodecs(@NotNull ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(encoder);
        configurer.defaultCodecs().jackson2JsonDecoder(decoder);
      }
    };
  }

  @Bean
  public Service gateway(CommonProperties properties) {
    return properties.getGateway();
  }

  @Bean
  public Map<Services, WebClient> serviceClients(
      WebClient.Builder webClientBuilder, CommonProperties properties) {
    return properties.getServices().entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, s -> webClientBuilder.baseUrl(s.getValue().endpoint()).build()));
  }

  @Bean
  @SneakyThrows
  public SslContext sslContext() {
    return SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
  }
}
