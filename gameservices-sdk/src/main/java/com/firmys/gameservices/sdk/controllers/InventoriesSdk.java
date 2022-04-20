package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class InventoriesSdk implements InventoriesApi {
    private final GatewayClient client;
    private final String uriPath = ServicePaths.INVENTORIES_PATH;
    private final ParameterizedTypeReference<Set<Inventory>> setTypeReference = new ParameterizedTypeReference<>() {};



    public InventoriesSdk(GatewayClient client) {
        this.client = client;
    }

    @Override // FIXME
    public Mono<Set<Inventory>> addMultipleInventory(Set<Inventory> inventories) {
        return client.getClient().post().uri(
                uriBuilder -> client.uriBuilderFunction(uriPath, null)
                        .apply(uriBuilder)).body(BodyInserters.fromValue(inventories)).retrieve().bodyToMono(setTypeReference);
    }

    @Override
    public Mono<Void> deleteMultipleInventory(Set<String> uuid, Set<Inventory> inventory) {
        return null;
    }

    @Override
    public Mono<Set<Inventory>> findMultipleInventory(Set<String> uuids) {
        return client.getClient().get().uri(
                uriBuilder -> client.uriBuilderFunction(uriPath, client.paramsFunction("uuid")
                        .apply(uuids)).apply(uriBuilder)).retrieve().bodyToMono(setTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> updateMultipleInventory(Set<Inventory> inventory) {
        return null;
    }
}
