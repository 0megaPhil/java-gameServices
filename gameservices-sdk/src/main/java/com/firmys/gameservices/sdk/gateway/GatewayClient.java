package com.firmys.gameservices.sdk.gateway;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.firmys.gameservices.sdk.Parameters;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class GatewayClient<R> {
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
     *
     * @return webclient for baseUrl
     */
    public WebClient getClient() {
        return webClientMap.computeIfAbsent(baseUrl, v -> WebClient.builder()
                .baseUrl(gatewayDetails.getGatewayAddress())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap(ServiceStrings.URL, gatewayDetails.getGatewayAddress()))
                .build());
    }

    /**
     * For temporarily adding path variables for a given request
     *
     * @param pathString path to add
     * @return new client which is pointed at new modified path
     */
    public GatewayClient<R> withPath(String pathString) {
        if (pathString.charAt(0) != '/') {
            pathString = "/" + pathString;
        }
        return new GatewayClient<>(this, baseUrl + pathString);
    }


    public GatewayDetails getGatewayDetails() {
        return gatewayDetails;
    }

    public Mono<R> get() {
        return get(null);
    }

    public Mono<R> get(Parameters parameters) {
        return requestSpecFunction(parameters, typeReference).apply(
                getClient().get());
    }

    public Mono<R> post() {
        return post(null);
    }

    public Mono<R> post(Parameters parameters) {
        return post(parameters, null);
    }

    public <S> Mono<R> post(Parameters parameters, S body) {
        return requestSpecFunction(parameters, body).apply(
                getClient().post());
    }

    public Mono<R> put() {
        return put(null);
    }

    public Mono<R> put(Parameters parameters) {
        return put(parameters, null);
    }

    public <S> Mono<R> put(Parameters parameters, S body) {
        return requestSpecFunction(parameters, body).apply(getClient().put());
    }

    public Mono<Void> delete() {
        return delete(null);
    }

    public Mono<Void> delete(Parameters parameters) {
        return requestSpecFunction(parameters, voidTypeReference).apply(getClient().delete());
    }

    /**
     * Breakout logic for handling requests which potentially contain a body
     * @param parameters build {@link Parameters} for passing URI parameters via request
     * @param body entity to be included in request as body
     * @param <S> type for body entity
     * @return mono of response from service
     */
    private <S> Function<WebClient.RequestBodyUriSpec, Mono<R>> requestSpecFunction(Parameters parameters, S body) {
        return spec -> bodyToMonoFunction(typeReference)
                .apply(errorHandleFunction()
                        .apply(requestBodyFunction(body)
                                .apply(spec.uri(uriBuilder -> uriBuilderFunction(baseUrl, parameters.get())
                                        .apply(uriBuilder)))
                                .retrieve()));
    }

    /**
     * Breakout logic for handling requests which cannot conatin a body
     * @param parameters build {@link Parameters} for passing URI parameters via request
     * @return mono of response from service
     */
    private <T> Function<WebClient.RequestHeadersUriSpec<?>, Mono<T>> requestSpecFunction(Parameters parameters,
                                                                                          ParameterizedTypeReference<T> typeReference) {
        return spec -> bodyToMonoFunction(typeReference)
                .apply(errorHandleFunction()
                        .apply(spec.uri(uriBuilder ->
                                uriBuilderFunction(baseUrl, parameters.get())
                                        .apply(uriBuilder)).retrieve()));
    }

    /**
     * Check for null body and apply if not null
     * @param body object to be sent in body of request, if body is not null
     * @param <S> type for body object
     * @return spec from WebClient for further use
     */
    private <S> Function<WebClient.RequestBodySpec, WebClient.RequestHeadersSpec<?>> requestBodyFunction(S body) {
        return spec -> {
            if (body != null) {
                return spec.body(BodyInserters.fromValue(body));
            }
            return spec;
        };
    }

    /**
     * Apply logic for generating mono of response with type T
     * @param typeReference ParameterizedTypeReference for type T
     * @param <T> type of response object
     * @return mono of type T
     */
    private <T> Function<WebClient.ResponseSpec, Mono<T>> bodyToMonoFunction(ParameterizedTypeReference<T> typeReference) {
        return spec -> spec.bodyToMono(typeReference);
    }

    /**
     * Apply error handling object for WebClient spec
     * @return WebClient spec with error handling applied
     */
    private Function<WebClient.ResponseSpec, WebClient.ResponseSpec> errorHandleFunction() {
        return spec -> spec.onStatus(HttpStatus::isError, response ->
                response.toEntity(GameServiceException.class).map(HttpEntity::getBody));
    }

}