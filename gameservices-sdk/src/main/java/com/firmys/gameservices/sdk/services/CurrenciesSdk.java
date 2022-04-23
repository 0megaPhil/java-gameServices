package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CurrenciesApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Currency;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class CurrenciesSdk extends AbstractSdk implements CurrenciesApi {
    private final ParameterizedTypeReference<Currency> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public CurrenciesSdk(GatewayClient client) {
        super(client, ServicePaths.CURRENCIES_PATH);
    }

    @Override
    public Mono<Set<Currency>> addMultipleCurrency(Set<Currency> currency) {
        return null;
    }

    @Override
    public Mono<Void> deleteMultipleCurrency(Set<String> uuid, Set<Currency> currency) {
        return null;
    }

    @Override
    public Mono<Set<Currency>> findMultipleCurrency(Set<String> uuid) {
        return null;
    }

    @Override
    public Mono<Set<Currency>> updateMultipleCurrency(Set<Currency> currency) {
        return null;
    }
}
