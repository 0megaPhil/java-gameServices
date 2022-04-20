//package com.firmys.gameservices.sdk.controllers;
//
//import com.firmys.gameservices.api.InventoriesApi;
//import com.firmys.gameservices.models.Inventory;
//import com.firmys.gameservices.sdk.client.GatewayClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import java.util.Set;
//
//@Component
//public class InventoriesSdk implements InventoriesApi {
//    private final GatewayClient client;
//
//    public InventoriesSdk(GatewayClient client) {
//        this.client = client;
//    }
//
//    @Override
//    public Mono<ResponseEntity<Flux<Inventory>>> find1(Set<String> uuid) {
//        ServerWebExchange webExchange = new
//        return InventoriesApi.super.find1(uuid, exchange);
//    }
//}
