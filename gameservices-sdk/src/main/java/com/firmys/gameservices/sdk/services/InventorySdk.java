package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;

import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class InventorySdk extends AbstractSdk<Inventory> implements InventoryApi {

    public InventorySdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.INVENTORY_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Inventory> addOwnedItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.ADD)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> addOwnedItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEMS).withPath(ServiceStrings.ADD)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeOwnedItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.CONSUME)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeOwnedItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEMS).withPath(ServiceStrings.CONSUME)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), item);

    }

    public Mono<Inventory> createInventory() {
        return createInventory(null);
    }

    @Override
    public Mono<Inventory> createInventory(Inventory inventory) {
        return getClient().post(Parameters.builder().build());
    }

    @Override
    public Mono<Inventory> creditCurrencyInventory(UUID pathUuid, UUID currency, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.CREDIT)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, currency)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), currency);

    }

    @Override
    public Mono<Inventory> debitCurrencyInventory(UUID pathUuid, UUID currency, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.DEBIT)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, currency)
                        .withParam(ServiceStrings.AMOUNT, amount).build(), currency);

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
    public Mono<Inventory> updateInventory(UUID pathUuid, Inventory inventory) {
        return getClient().withPath(pathUuid).put(Parameters.builder().build(), inventory);
    }
}
