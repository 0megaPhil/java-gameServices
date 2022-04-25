//package com.firmys.gameservices.sdk.services;
//
//import com.firmys.gameservices.api.CurrencyApi;
//import com.firmys.gameservices.common.ServicePaths;
//import com.firmys.gameservices.models.Currency;
//import com.firmys.gameservices.sdk.gateway.GatewayClient;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//public class CurrencySdk extends AbstractSdk implements CurrencyApi {
//    private final ParameterizedTypeReference<Currency> typeReference = new ParameterizedTypeReference<>() {};
//    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};
//
//    public CurrencySdk(GatewayClient client) {
//        super(client, ServicePaths.CURRENCY_PATH);
//    }
//
//    @Override
//    public Mono<Currency> addCurrency(Currency currency) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> deleteByUuidCurrency(String uuid) {
//        return null;
//    }
//
//    @Override
//    public Mono<Void> deleteCurrency(String uuid, Currency currency) {
//        return null;
//    }
//
//    @Override
//    public Mono<Currency> findByUuidParamCurrency(String uuid) {
//        return null;
//    }
//
//    @Override
//    public Mono<Currency> findByUuidPathCurrency(String uuid) {
//        return null;
//    }
//
//    @Override
//    public Mono<Currency> updateByUuidCurrency(String uuid, Currency currency) {
//        return null;
//    }
//
//    @Override
//    public Mono<Currency> updateCurrency(Currency currency) {
//        return null;
//    }
//}
