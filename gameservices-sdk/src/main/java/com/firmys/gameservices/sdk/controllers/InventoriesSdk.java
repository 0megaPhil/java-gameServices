package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class InventoriesSdk implements InventoriesApi {
    private final GatewayClient client;
    private final String uriPath = ServicePaths.INVENTORIES_PATH;
    private final ParameterizedTypeReference<Set<Inventory>> setTypeReference = new ParameterizedTypeReference<>() {};

    public InventoriesSdk(GatewayClient client) {
        this.client = client;
    }

    @Override
    public Mono<Set<Inventory>> addMultipleInventory(Integer amount) {
        return client.getClient().post().uri(
                uriBuilder -> client.uriBuilderFunction(uriPath, client.paramsFunction(ServicePaths.AMOUNT).apply(Set.of(amount.toString())))
                        .apply(uriBuilder)).retrieve().bodyToMono(setTypeReference);
    }

    @Override
    public Mono<Void> deleteMultipleInventory(Set<String> uuids, Set<Inventory> inventories) {
        return client.getClient().method(HttpMethod.DELETE).uri(
                uriBuilder -> client.uriBuilderFunction(uriPath, client.paramsFunction(ServicePaths.UUID).apply(uuids))
                        .apply(uriBuilder)).body(BodyInserters.fromValue(inventories)).retrieve().bodyToMono(Void.class);
    }

    @Override
    public Mono<Set<Inventory>> findMultipleInventory(Set<String> uuids) {
        return client.getClient().get().uri(
                uriBuilder -> client.uriBuilderFunction(uriPath, client.paramsFunction(ServicePaths.UUID)
                        .apply(uuids)).apply(uriBuilder)).retrieve().bodyToMono(setTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> updateMultipleInventory(Set<Inventory> inventory) {
        return Mono.error(new RuntimeException("NOT IMPLEMENTED"));
    }
}
