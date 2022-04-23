package com.firmys.gameservices.sdk.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Component
public class GatewayClient {
    private final WebClient client;
    private String baseUri;

    public GatewayClient(@Value("${gameservices.gateway.host}") String host,
                         @Value("${gameservices.gateway.port}") String port) {
        this.client = WebClient.builder()
                .baseUrl("http://" + host + ":" + port)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://" + host + ":" + port))
                .build();
    }

    // TODO - consider structure of this class
    public GatewayClient withBaseUri(String baseUri) {
        this.baseUri = baseUri;
        return this;
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

    public <R> Mono<R> get(String uriPath, LinkedMultiValueMap<String, String> params, ParameterizedTypeReference<R> typeReference) {
        return getClient().get().uri(
                uriBuilder -> uriBuilderFunction(uriPath, params).apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public <R> Mono<R> post(String uriPath, ParameterizedTypeReference<R> typeReference) {
        return getClient().post().uri(
                uriBuilder -> uriBuilder.path(uriPath).build()).retrieve().bodyToMono(typeReference);
    }

    public <R> Mono<R> post(String uriPath, LinkedMultiValueMap<String, String> params, ParameterizedTypeReference<R> typeReference) {
        return getClient().post().uri(
                uriBuilder -> uriBuilderFunction(uriPath, params)
                        .apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public <S, R> Mono<R> post(String uriPath, LinkedMultiValueMap<String, String> params, ParameterizedTypeReference<R> typeReference, S body) {
        return getClient().post().uri(
                uriBuilder -> uriBuilderFunction(uriPath, params)
                        .apply(uriBuilder)).body(BodyInserters.fromValue(body)).retrieve().bodyToMono(typeReference);
    }

    public <R> Mono<R> put(String uriPath, LinkedMultiValueMap<String, String> params, ParameterizedTypeReference<R> typeReference) {
        return getClient().put().uri(
                uriBuilder -> uriBuilderFunction(uriPath, params)
                        .apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public <R> Mono<R> delete(String uriPath, ParameterizedTypeReference<R> typeReference) {
        return getClient().delete().uri(
                uriBuilder -> uriBuilder.path(uriPath).build()).retrieve().bodyToMono(typeReference);
    }

    public <R> Mono<R> delete(String uriPath, LinkedMultiValueMap<String, String> params, ParameterizedTypeReference<R> typeReference) {
        return getClient().delete().uri(
                uriBuilder -> uriBuilderFunction(uriPath, params).apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public Function<String, String> applyPathVar(String pathVar) {
        return path -> path + "/" + pathVar;
    }

}
