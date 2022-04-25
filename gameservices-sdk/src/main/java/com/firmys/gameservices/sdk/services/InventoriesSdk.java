package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Inventory;

import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class InventoriesSdk extends AbstractSdk<Set<Inventory>> implements InventoriesApi {

    public InventoriesSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.INVENTORIES_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Set<Inventory>> addMultipleInventory(Integer amount) {
        return getClient().post(
                Parameters.builder().withParam(ServiceStrings.AMOUNT, amount.toString()).build());
    }

    @Override
    public Mono<Void> deleteMultipleInventory(Set<String> uuids, Set<Inventory> inventories) {
        Set<String> combinedUuids = Optional.ofNullable(uuids).orElse(new HashSet<>());
        combinedUuids.addAll(Optional.ofNullable(inventories).orElse(new HashSet<>()).stream()
                .map(i -> i.getUuid().toString()).collect(Collectors.toSet()));
        return getClient().delete(
                Parameters.builder().withParam(ServiceStrings.UUID, combinedUuids).build());
    }

    @Override
    public Mono<Set<Inventory>> findMultipleInventory(Set<String> uuids) {
        return getClient().get(
                Parameters.builder().withParam(ServiceStrings.UUID, uuids).build());
    }

    @Override
    public Mono<Set<Inventory>> updateMultipleInventory(Set<Inventory> inventory) {
        return Mono.error(new RuntimeException("NOT IMPLEMENTED"));
    }
}
