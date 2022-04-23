package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.ItemApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import reactor.core.publisher.Mono;

@Component
public class ItemSdk extends AbstractSdk implements ItemApi {
    private final ParameterizedTypeReference<Item> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public ItemSdk(GatewayClient client) {
        super(client, ServicePaths.ITEM_PATH);
    }

    @Override
    public Mono<Item> addItem(Item item) {
        return getClient().post(getBasePath(), new LinkedMultiValueMap<>(), typeReference, item);
    }

    @Override
    public Mono<Void> deleteByUuidItem(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteItem(String uuid, Item item) {
        return null;
    }

    @Override
    public Mono<Item> findByUuidParamItem(String uuid) {
        return null;
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
