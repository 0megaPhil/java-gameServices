package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoriesApi;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.models.Inventory;

import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Component
public class InventoriesSdk extends AbstractSdk<Set<Inventory>> implements InventoriesApi {

    public InventoriesSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceConstants.INVENTORIES_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Set<Inventory>> createSetInventory(Integer amount) {
        return getClient().post(
                Parameters.builder().withParam(ServiceConstants.AMOUNT, amount).build());
    }

    @Override
    public Mono<Void> deleteSetInventory(Set<UUID> uuid) {
        return getClient().delete(
                Parameters.builder().withParam(ServiceConstants.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Inventory>> findSetInventory(Set<UUID> uuid) {
        return getClient().get(
                Parameters.builder().withParam(ServiceConstants.UUID, uuid).build());

    }

    @Override
    public Mono<Set<Inventory>> updateSetInventory(Set<Inventory> inventory) {
        return Mono.error(new RuntimeException("NOT IMPLEMENTED"));
    }
}
