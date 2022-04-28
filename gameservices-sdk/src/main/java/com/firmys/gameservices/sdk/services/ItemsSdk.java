package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.ItemsApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Item;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class ItemsSdk extends AbstractSdk<Set<Item>> implements ItemsApi {

    public ItemsSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.ITEMS_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Set<Item>> createSetItem(Set<Item> item) {
        return getClient().post(Parameters.builder().build(), item);
    }

    @Override
    public Mono<Void> deleteSetItem(Set<UUID> uuid) {
        return getClient().delete(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Item>> findAllItem(Map<String, String> queryMap) {
        Parameters.Builder builder = new Parameters.Builder();
        queryMap.forEach(builder::withParam);
        return getClient().withPath(ServiceStrings.QUERY_PATH).get(builder.build());
    }

    @Override
    public Mono<Set<Item>> findSetItem(Set<UUID> uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Item>> updateSetItem(Set<Item> item) {
        return getClient().put(Parameters.builder().build(), item);
    }

}
