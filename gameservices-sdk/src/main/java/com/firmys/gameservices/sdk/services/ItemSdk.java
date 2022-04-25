package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.ItemApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Component
public class ItemSdk extends AbstractSdk<Item> implements ItemApi {

    public ItemSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.ITEM_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Item> addItem(Item item) {
        return getClient().post(Parameters.builder().build(), item);
    }

    @Override
    public Mono<Void> deleteByUuidItem(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteItem(String uuid, Item item) {
        String foundUuid = Optional.ofNullable(uuid).orElse(item.getUuid().toString());
        return getClient().delete(
                Parameters.builder().withParam(ServiceStrings.UUID, foundUuid).build());
    }

    @Override
    public Mono<Item> findByUuidParamItem(String uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Item> findByUuidPathItem(String uuid) {
        return null;
    }

    @Override
    public Mono<Item> updateByUuidItem(String uuid, Item item) {
        return null;
    }

    @Override
    public Mono<Item> updateItem(Item item) {
        return null;
    }
}
