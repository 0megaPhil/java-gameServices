package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CurrenciesApi;
import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.UUID;

@Component
public class CurrenciesSdk extends AbstractSdk<Set<Currency>> implements CurrenciesApi {

    public CurrenciesSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceStrings.CURRENCIES_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Set<Currency>> createSetCurrency(Set<Currency> currency) {
        return getClient().post(Parameters.builder().build(), currency);
    }

    @Override
    public Mono<Void> deleteSetCurrency(Set<UUID> uuid) {
        return getClient().delete(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Currency>> findSetCurrency(Set<UUID> uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceStrings.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Currency>> updateSetCurrency(Set<Currency> currency) {
        return getClient().put(Parameters.builder().build(), currency);
    }
}
