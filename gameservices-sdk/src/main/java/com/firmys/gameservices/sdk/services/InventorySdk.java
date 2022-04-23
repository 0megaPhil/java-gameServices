package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.InventoryApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.models.OwnedItems;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InventorySdk extends AbstractSdk implements InventoryApi {

    private final ParameterizedTypeReference<Inventory> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public InventorySdk(GatewayClient client) {
        super(client, ServicePaths.INVENTORY_PATH);
    }


    public Mono<Inventory> addInventory() {
        return addInventory(null);
    }
    @Override
    public Mono<Inventory> addInventory(Inventory inventory) {
        return getClient().post(getBasePath(), typeReference);
    }

    @Override
    public Mono<Inventory> addOwnedItemInventory(String uuid, Integer amount, Item item) {
        return null;
    }

    @Override
    public Mono<Inventory> addOwnedItemsInventory(String uuid, Integer amount, List<Item> item) {
        return null;
    }

    @Override
    public Mono<Inventory> consumeOwnedItemInventory(String uuid, Integer amount, Item item) {
        return null;
    }

    @Override
    public Mono<Inventory> consumeOwnedItemsInventory(String uuid, Integer amount, List<Item> item) {
        return null;
    }

    @Override
    public Mono<Inventory> creditCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return null;
    }

    @Override
    public Mono<Inventory> debitCurrencyByUuidInventory(String uuid, Integer amount, Currency currency) {
        return null;
    }

    @Override
    public Mono<Void> deleteByUuidInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteInventory(String uuid, Inventory inventory) {
        String foundUuid = Optional.ofNullable(uuid).orElse(inventory.getUuid().toString());
        return getClient().delete(getClient().applyPathVar(foundUuid).apply(getBasePath()), getClient().paramsFunction(ServicePaths.UUID)
                .apply(Set.of(foundUuid)), voidTypeReference);
    }

    @Override
    public Mono<Inventory> findByUuidParamInventory(String uuid) {
        return null;
    }

    @Override
    public Mono<Inventory> findByUuidPathInventory(String uuid) {
        return null;
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
