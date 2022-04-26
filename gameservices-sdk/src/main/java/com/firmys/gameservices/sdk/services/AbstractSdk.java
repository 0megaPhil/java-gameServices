package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.sdk.gateway.GatewayClient;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractSdk<R> {
    private final GatewayClient<R> client;
    private GatewayDetails gatewayDetails;
    private String uriPath;
    private ParameterizedTypeReference<R> typeReference;

    public AbstractSdk(GatewayDetails gatewayDetails, String uriPath, ParameterizedTypeReference<R> typeReference) {
        this.gatewayDetails = gatewayDetails;
        this.uriPath = uriPath;
        this.typeReference = typeReference;
        this.client = new GatewayClient<>(gatewayDetails, uriPath, typeReference);
    }

    public GatewayClient<R> getClient() {
        return client;
    }

    public <S extends Set> GatewayClient<S> getClient(ParameterizedTypeReference<S> typeReference) {
        return new GatewayClient<>(gatewayDetails, uriPath, typeReference);
    }

}
