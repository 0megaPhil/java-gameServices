//package com.firmys.gameservices.sdk.services;
//
//import com.firmys.gameservices.api.ItemsApi;
//import com.firmys.gameservices.common.ServicePaths;
//import com.firmys.gameservices.models.Item;
//import com.firmys.gameservices.sdk.gateway.GatewayClient;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.util.Set;
//
//@Component
//public class ItemsSdk extends AbstractSdk implements ItemsApi {
//    private final ParameterizedTypeReference<Item> typeReference = new ParameterizedTypeReference<>() {};
//    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};
//
//    public ItemsSdk(GatewayClient client) {
//        super(client, ServicePaths.ITEMS_PATH);
//    }
//
//
//    @Override
//    public Mono<Set<Item>> addMultipleItem(Set<Item> item) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> deleteMultipleItem(Set<String> uuid, Set<Item> item) {
//        return null;
//    }
//
//    @Override
//    public Mono<Set<Item>> findMultipleItem(Set<String> uuid) {
//        return null;
//    }
//
//    @Override
//    public Mono<Set<Item>> updateMultipleItem(Set<Item> item) {
//        return null;
//    }
//}
