package com.firmys.gameservices.common.config;

import static reactor.netty.http.client.HttpClient.*;

import com.firmys.gameservices.common.CommonProperties;
import com.firmys.gameservices.common.CommonProperties.Service;
import com.firmys.gameservices.common.Services;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties(CommonProperties.class)
public class WebClientConfig {

  @Bean
  public WebClient.Builder webClientBuilder(SslContext sslContext) {
    HttpClient httpClient = create().secure(sslSpec -> sslSpec.sslContext(sslContext));
    ClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(httpClient);
    return WebClient.builder().clientConnector(clientHttpConnector);
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
