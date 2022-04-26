package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.models.OwnedItem;
import com.firmys.gameservices.models.OwnedItems;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InventorySdk extends AbstractSdk<Inventory> implements InventoryApi {

    public InventorySdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.INVENTORY_PATH, new ParameterizedTypeReference<>() {});
    }

    public Mono<Inventory> addInventory() {
        return getClient().post(Parameters.builder().build());
    }

    @Override
    public Mono<Inventory> addInventory(Inventory inventory) {
        return getClient().post(Parameters.builder().build(),
                Optional.ofNullable(inventory).orElse(new Inventory()));
    }

    /**
     * Add OwnedItem of type Item to an Inventory
     * @param uuid  (required) uuid of {@link Inventory}
     * @param amount  (optional) amount of {@link OwnedItem} to be added for {@link Item}
     * @param item  (optional)
     * @return Inventory after with modifications
     */
    @Override
    public Mono<Inventory> addOwnedItemInventory(String uuid, Integer amount, Item item) {
        return getClient().withPath(uuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.ADD)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item.getUuid().toString())
                .withParam(ServiceStrings.AMOUNT, amount.toString()).build(), item);
    }

    @Override
    public Mono<Inventory> addOwnedItemsInventory(String uuid, Integer amount, List<Item> items) {
       return getClient().withPath(uuid).withPath(ServiceStrings.ITEMS).withPath(ServiceStrings.ADD)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, items.stream()
                                .map(i -> i.getUuid().toString()).collect(Collectors.toSet()))
                        .withParam(ServiceStrings.AMOUNT, amount.toString()).build(), null);
    }

    @Override
    public Mono<Inventory> consumeOwnedItemInventory(String uuid, Integer amount, Item item) {
        return getClient().withPath(uuid).withPath(ServiceStrings.ITEM).withPath(ServiceStrings.CONSUME)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, item.getUuid().toString())
                        .withParam(ServiceStrings.AMOUNT, amount.toString()).build(), item);
    }

    @Override
    public Mono<Inventory> consumeOwnedItemsInventory(String uuid, Integer amount, List<Item> items) {
        return getClient().withPath(uuid).withPath(ServiceStrings.ITEMS).withPath(ServiceStrings.CONSUME)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, items.stream()
                                .map(i -> i.getUuid().toString()).collect(Collectors.toSet()))
                        .withParam(ServiceStrings.AMOUNT, amount.toString()).build(), null);
    }

    @Override
    public Mono<Inventory> creditCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return getClient().withPath(uuid).withPath(ServiceStrings.CURRENCY).withPath(ServiceStrings.CREDIT)
                .put(Parameters.builder()
                        .withParam(ServiceStrings.UUID, currency.getUuid().toString())
                        .withParam(ServiceStrings.AMOUNT, amount.toString()).build());
    }

    @Override
    public Mono<Inventory> debitCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return getClient().withPath(uuid).withPath(ServiceStrings.CURRENCY).withPath(ServiceStrings.DEBIT)
                .put(Parameters.builder().withParam(ServiceStrings.UUID, currency.getUuid().toString())
                        .withParam(ServiceStrings.AMOUNT, amount.toString()).build(), currency);
    }

    @Override
    public Mono<Void> deleteByUuidInventory(String uuid) {
        return getClient().delete(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Void> deleteInventory(String uuid, Inventory inventory) {
        String foundUuid = Optional.ofNullable(uuid).orElse(inventory.getUuid().toString());
        return getClient().delete(Parameters.builder().withParam(ServiceStrings.UUID, foundUuid).build());
    }

    @Override
    public Mono<Inventory> findByUuidParamInventory(String uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Inventory> findByUuidPathInventory(String uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Inventory>> findInventoriesWithItemByUuidParamInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Set<Inventory>> findInventoriesWithItemByUuidPathInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<OwnedItems> getOwnedItemsByInventoryUuidInventory(String uuid, Inventory requestBody) {
        return null;
    }

    @Override
    public Mono<Inventory> updateByUuidInventory(String uuid, Inventory inventory) {
        return null;
    }

    @Override
    public Mono<Inventory> updateInventory(Inventory inventory) {
        return null;
    }
}
