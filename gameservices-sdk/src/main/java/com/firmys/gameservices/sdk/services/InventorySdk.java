package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;

import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

public class InventorySdk extends AbstractSdk<Inventory> implements InventoryApi {

    public InventorySdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceConstants.INVENTORY_PATH, new ParameterizedTypeReference<>() {
        });
    }

    public Mono<Inventory> createInventory() {
        return createInventory(null);
    }

    @Override
    public Mono<Inventory> addConsumableItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.ADD)
                .put(Parameters.builder().withParam(ServiceConstants.ITEM, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> addConsumableItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEMS).withPath(ServiceConstants.ADD)
                .put(Parameters.builder().withParam(ServiceConstants.ITEM, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeConsumableItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.CONSUME)
                .put(Parameters.builder().withParam(ServiceConstants.ITEM, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeConsumableItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEMS).withPath(ServiceConstants.CONSUME)
                .put(Parameters.builder().withParam(ServiceConstants.ITEM, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> createInventory(Inventory inventory) {
        return getClient().post(Parameters.builder().build());
    }

    @Override
    public Mono<Inventory> creditTransactionalCurrencyInventory(UUID pathUuid, UUID currency, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.CURRENCY).withPath(ServiceConstants.CREDIT)
                .put(Parameters.builder().withParam(ServiceConstants.CURRENCY, currency)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), currency);

    }

    @Override
    public Mono<Inventory> debitTransactionalCurrencyInventory(UUID pathUuid, UUID currency, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.CURRENCY).withPath(ServiceConstants.DEBIT)
                .put(Parameters.builder().withParam(ServiceConstants.CURRENCY, currency)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), currency);

    }

    @Override
    public Mono<Void> deleteInventory(UUID pathUuid) {
        return getClient().withPath(pathUuid).delete(Parameters.builder().build());
    }

    @Override
    public Mono<Inventory> findInventory(UUID pathUuid) {
        return getClient().withPath(pathUuid).get(Parameters.builder().build());
    }

    @Override
    public Mono<Inventory> updateInventory(Inventory inventory) {
        return getClient().put(Parameters.builder().build(), inventory);
    }
}
