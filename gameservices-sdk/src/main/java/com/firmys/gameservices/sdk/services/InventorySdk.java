package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.models.OwnedItem;
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

    /**
     * Add {@link OwnedItem} to {@link Inventory}, which represents a unique consumable instance of an {@link Item}
     * @param pathUuid  (required) {@link UUID} for Inventory to be modified
     * @param item  (required) UUID of Item to be added as an OwnedItem to Inventory
     * @param amount  (required) number of OwnedItem of Item to be added to Inventory
     * @return modified Inventory
     */
    @Override
    public Mono<Inventory> addOwnedItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.ADD)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> addOwnedItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEMS).withPath(ServiceConstants.ADD)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeOwnedItemInventory(UUID pathUuid, UUID item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.CONSUME)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

    }

    @Override
    public Mono<Inventory> consumeOwnedItemsInventory(UUID pathUuid, Set<UUID> item, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEMS).withPath(ServiceConstants.CONSUME)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, item)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), item);

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
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.CREDIT)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, currency)
                        .withParam(ServiceConstants.AMOUNT, amount).build(), currency);

    }

    @Override
    public Mono<Inventory> debitCurrencyInventory(UUID pathUuid, UUID currency, Integer amount) {
        return getClient().withPath(pathUuid).withPath(ServiceConstants.ITEM).withPath(ServiceConstants.DEBIT)
                .put(Parameters.builder().withParam(ServiceConstants.UUID, currency)
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
    public Mono<Inventory> updateInventory(UUID pathUuid, Inventory inventory) {
        return getClient().withPath(pathUuid).put(Parameters.builder().build(), inventory);
    }
}
