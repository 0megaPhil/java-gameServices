package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CurrencyApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayClient;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class CurrencySdk extends AbstractSdk<Currency> implements CurrencyApi {

    public CurrencySdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.CURRENCY_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Currency> addCurrency(Currency currency) {
        return getClient().post(Parameters.builder().build(), currency);
    }

    @Override
    public Mono<Void> deleteByUuidCurrency(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteCurrency(String uuid, Currency currency) {
        return null;
    }

    @Override
    public Mono<Currency> findByUuidParamCurrency(String uuid) {
        return null;
    }

    @Override
    public Mono<Currency> findByUuidPathCurrency(String uuid) {
        return null;
    }

    @Override
    public Mono<Currency> updateByUuidCurrency(String uuid, Currency currency) {
        return null;
    }

    @Override
    public Mono<Currency> updateCurrency(Currency currency) {
        return null;
    }
}
