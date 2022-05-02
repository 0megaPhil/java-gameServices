package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.ItemApi;
import com.firmys.gameservices.common.ServiceConstants;
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
        super(gatewayDetails, ServiceConstants.ITEM_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Item> createItem(Item item) {
        return getClient().post(Parameters.builder().build(), item);
    }

    @Override
    public Mono<Void> deleteItem(UUID pathUuid) {
        return getClient().withPath(pathUuid).delete(Parameters.builder().build());
    }

    @Override
    public Mono<Item> findItem(UUID pathUuid) {
        return getClient().withPath(pathUuid).get(Parameters.builder().build());
    }

    @Override
    public Mono<Item> updateItem(Item item) {
        return getClient().put(Parameters.builder().build(), item);
    }

}
