package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;

import java.util.Set;

public abstract class AbstractSdk {
    private final GatewayClient client;
    private final String uriPath;

    public AbstractSdk(GatewayClient client, String uriPath) {
        this.client = client;
        this.uriPath = uriPath;
    }

    public GatewayClient getClient() {
        return client;
    }

    public String getUriPath() {
        return uriPath;
    }

}
