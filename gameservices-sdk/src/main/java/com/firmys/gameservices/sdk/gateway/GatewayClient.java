package com.firmys.gameservices.sdk.gateway;

import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.sdk.Parameters;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.function.Predicate;

public class GatewayClient<R extends GameEntity> {
    private static final Map<String, WebClient> webClientMap = new ConcurrentHashMap<>();
    private final ParameterizedTypeReference<R> typeReference;
    private final ParameterizedTypeReference<Void> voidTypeReference;
    private final ParameterizedTypeReference<GameServiceError> errorTypeReference;
    private final String baseUrl;
    private final GatewayDetails gatewayDetails;

    public GatewayClient(GatewayDetails gatewayDetails, String baseUrl, ParameterizedTypeReference<R> typeReference) {
        this.baseUrl = baseUrl;
        this.typeReference = typeReference;
        this.voidTypeReference = new ParameterizedTypeReference<>() {};
        this.errorTypeReference = new ParameterizedTypeReference<>() {};
        this.gatewayDetails = gatewayDetails;
    }

    private GatewayClient(GatewayClient<R> gatewayClient, String baseUrl) {
        this.baseUrl = baseUrl;
        this.typeReference = gatewayClient.typeReference;
        this.voidTypeReference = gatewayClient.voidTypeReference;
        this.errorTypeReference = gatewayClient.errorTypeReference;
        this.gatewayDetails = gatewayClient.gatewayDetails;
    }

    public Function<UriBuilder, URI> uriBuilderFunction(String uriPath, LinkedMultiValueMap<String, String> params) {
        return uriBuilder -> uriBuilder.queryParams(params).path(uriPath).build();
    }

    /**
     * Build {@link WebClient} only as needed
     * @return webclient for baseUrl
     */
    public WebClient getClient() {
        return webClientMap.computeIfAbsent(baseUrl, v -> WebClient.builder()
                .baseUrl(gatewayDetails.getGatewayAddress())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap(ServiceStrings.URL, gatewayDetails.getGatewayAddress()))
                .build());
    }

    public GatewayClient<R> withPath(String pathString) {
        if(pathString.charAt(0) != '/') {
            pathString = "/" + pathString;
        }
        return new GatewayClient<>(this, baseUrl + pathString);
    }

    public Mono<R> get(Parameters parameters) {
        return getClient().get().uri(
                uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                        .apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public Mono<R> post() {
        return getClient().post().uri(
                uriBuilder -> uriBuilder.path(baseUrl).build()).retrieve().bodyToMono(typeReference);
    }

    public Mono<R> post(Parameters parameters) {
        return getClient().post().uri(
                uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                        .apply(uriBuilder)).retrieve().bodyToMono(typeReference);
    }

    public <S> Mono<R> post(Parameters parameters, S body) {
        if(body == null) {
            return post(parameters);
        }
        return getClient().post().uri(
                uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                        .apply(uriBuilder)).body(BodyInserters.fromValue(body)).retrieve().bodyToMono(typeReference);
    }

    public Mono<R> put(Parameters parameters) {
        return getClient().put().uri(
                uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                        .apply(uriBuilder)).retrieve()
//                .onStatus(HttpStatus::isError, response -> {
//                    HttpStatus errorCode = response.statusCode();
//                    return response.bodyToMono(typeReference)
//                            .onErrorMap(error -> new Exception("Throw your generic exception over here if there is any error in deserialization"))
//                            .flatMap(error -> Mono.error(GameServiceException.builder.withGameServiceError()));
//            return mono.map(m -> {
//                System.out.println(m.getGameServiceError());
//                m.printStackTrace();
//                return m;
//            });
//        })
                .bodyToMono(typeReference)
                .onErrorMap(Predicate.not(GameServiceException.class::isInstance), throwable -> new GameServiceException());
    }

    ExecutorService executor = Executors.newFixedThreadPool(10);

    public void consumeError(Mono<? extends Throwable> mono) {
        executor.execute(() -> mono.block().printStackTrace());
    }

    public <S> Mono<R> put(Parameters parameters, S body) {
        if(body == null) {
            return put(parameters);
        }
        return getClient().put().uri(
                uriBuilder -> {
                    URI uri = uriBuilderFunction(baseUrl, parameters.get())
                            .apply(uriBuilder);
                    return uri;
                }).body(BodyInserters.fromValue(body)).retrieve().bodyToMono(typeReference);
    }

    public static void logTraceResponse(ClientResponse response) {
        response.bodyToMono(GameServiceError.class)
                .publishOn(Schedulers.elastic())
                .subscribe(body -> System.out.println("Response body:  " + body));
    }

    public Mono<Void> delete(Parameters parameters) {
        return getClient().delete().uri(
                uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                        .apply(uriBuilder)).retrieve().bodyToMono(voidTypeReference);
    }

    public GatewayDetails getGatewayDetails() {
        return gatewayDetails;
    }
}
