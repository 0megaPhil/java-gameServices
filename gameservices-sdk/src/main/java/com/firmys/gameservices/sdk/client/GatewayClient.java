package com.firmys.gameservices.sdk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

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

    public Function<UriBuilder, URI> uriBuilderFunction(String uriPath, LinkedMultiValueMap<String, String> params) {
        return uriBuilder -> uriBuilder.queryParams(params).path(uriPath).build();
    }

    public Function<Set<String>, LinkedMultiValueMap<String, String>> paramsFunction(String paramName) {
        LinkedMultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
        return params -> {
            paramsMap.put(paramName, Optional.ofNullable(params).orElse(new HashSet<>()).stream().toList());
            return paramsMap;
        };
    }

    public WebClient getClient() {
        return client;
    }
}
