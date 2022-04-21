package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class InventoriesSdk extends AbstractSdk implements InventoriesApi {

    private final ParameterizedTypeReference<Set<Inventory>> setTypeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Inventory> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public InventoriesSdk(GatewayClient client) {
        super(client, ServicePaths.INVENTORIES_PATH);
    }

    @Override
    public Mono<Set<Inventory>> addMultipleInventory(Integer amount) {
        return getClient().post(getUriPath(),
                getClient().paramsFunction(ServicePaths.AMOUNT).apply(Set.of(amount.toString())), setTypeReference);
    }

    @Override
    public Mono<Void> deleteMultipleInventory(Set<String> uuids, Set<Inventory> inventories) {
        return getClient().delete(getUriPath(), getClient().paramsFunction(ServicePaths.UUID).apply(uuids), voidTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> findMultipleInventory(Set<String> uuids) {
        return getClient().get(getUriPath(),
                getClient().paramsFunction(ServicePaths.UUID).apply(uuids),
                setTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> updateMultipleInventory(Set<Inventory> inventory) {
        return Mono.error(new RuntimeException("NOT IMPLEMENTED"));
    }
}
