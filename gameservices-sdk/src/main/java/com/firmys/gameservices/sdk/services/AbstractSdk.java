package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.sdk.gateway.GatewayClient;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Set;

public abstract class AbstractSdk<R> {
    private final GatewayClient<R> client;
    private final GatewayDetails gatewayDetails;
    private final String uriPath;
    private final ParameterizedTypeReference<R> typeReference;

    public AbstractSdk(GatewayDetails gatewayDetails, String uriPath, ParameterizedTypeReference<R> typeReference) {
        this.gatewayDetails = gatewayDetails;
        this.uriPath = uriPath;
        this.typeReference = typeReference;
        this.client = new GatewayClient<>(gatewayDetails, uriPath, typeReference);
    }

    public GatewayClient<R> getClient() {
        return client;
    }

    public <S extends Set<S>> GatewayClient<S> getClient(ParameterizedTypeReference<S> typeReference) {
        return new GatewayClient<>(gatewayDetails, uriPath, typeReference);
    }

}
