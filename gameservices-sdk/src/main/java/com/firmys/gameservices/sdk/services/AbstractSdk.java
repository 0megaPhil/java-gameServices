package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.common.GameEntity;
import com.firmys.gameservices.sdk.gateway.GatewayClient;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;

public abstract class AbstractSdk<R extends GameEntity> {
    private final GatewayClient<R> client;

    public AbstractSdk(GatewayDetails gatewayDetails, String uriPath, ParameterizedTypeReference<R> typeReference) {
        this.client = new GatewayClient<>(gatewayDetails, uriPath, typeReference);
    }

    public GatewayClient<R> getClient() {
        return client;
    }

}
