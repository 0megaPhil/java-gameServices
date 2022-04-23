package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.client.GatewayClient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        return getClient().post(getBasePath(),
                getClient().paramsFunction(ServicePaths.AMOUNT).apply(Set.of(amount.toString())), setTypeReference);
    }

    @Override
    public Mono<Void> deleteMultipleInventory(Set<String> uuids, Set<Inventory> inventories) {
        Set<String> combinedUuids = Optional.ofNullable(uuids).orElse(new HashSet<>());
        combinedUuids.addAll(Optional.ofNullable(inventories).orElse(new HashSet<>()).stream()
                .map(i -> i.getUuid().toString()).collect(Collectors.toSet()));
        return getClient().delete(getBasePath(), getClient().paramsFunction(ServicePaths.UUID).apply(combinedUuids), voidTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> findMultipleInventory(Set<String> uuids) {
        return getClient().get(getBasePath(),
                getClient().paramsFunction(ServicePaths.UUID).apply(uuids),
                setTypeReference);
    }

    @Override
    public Mono<Set<Inventory>> updateMultipleInventory(Set<Inventory> inventory) {
        return Mono.error(new RuntimeException("NOT IMPLEMENTED"));
    }
}
