package com.firmys.gameservices.sdk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Component
public class GatewayClient {
    private final WebClient client;

    public GatewayClient(@Value("${gameservices.gateway.host}") String host,
                         @Value("${gameservices.gateway.port}") String port) {
        this.client = WebClient.builder()
                .baseUrl("http://" + host + ":" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://" + host + ":" + port))
                .build();
    }

    public WebClient getClient() {
        return client;
    }
}
