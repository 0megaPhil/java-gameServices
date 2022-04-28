package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.ItemApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ItemSdk extends AbstractSdk<Item> implements ItemApi {

    public ItemSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.ITEM_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Item> createItem(Item item) {
        return getClient().post(Parameters.builder().build(), item);
    }

    @Override
    public Mono<Void> deleteItem(UUID uuid) {
        return getClient().delete(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Item> findItem(UUID uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Item> updateItem(UUID uuid, Item item) {
        return getClient().put(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build(), item);
    }
}
