package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CurrencyApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CurrencySdk extends AbstractSdk<Currency> implements CurrencyApi {

    public CurrencySdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.CURRENCY_PATH, new ParameterizedTypeReference<>() {});
    }

    @Override
    public Mono<Currency> createCurrency(Currency currency) {
        return getClient().post(Parameters.builder().build(), currency);

    }

    @Override
    public Mono<Void> deleteCurrency(UUID pathUuid) {
        return getClient().withPath(pathUuid).delete(Parameters.builder().build());
    }

    @Override
    public Mono<Currency> findCurrency(UUID pathUuid) {
        return getClient().withPath(pathUuid).get(Parameters.builder().build());
    }

    @Override
    public Mono<Currency> updateCurrency(UUID pathUuid, Currency currency) {
        return getClient().withPath(pathUuid).put(Parameters.builder().build(), currency);
    }
}
