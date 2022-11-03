package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CharactersApi;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Component
public class CharactersSdk extends AbstractSdk<Set<Character>> implements CharactersApi {

    public CharactersSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceConstants.CHARACTERS_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Set<Character>> createSetCharacter(Set<Character> character) {
        return getClient().post(Parameters.builder().build(), character);
    }

    @Override
    public Mono<Void> deleteSetCharacter(Set<UUID> uuid) {
        return getClient().delete(Parameters.builder().withParam(ServiceConstants.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Character>> findAllCharacter(Map<String, String> queryMap) {
        Parameters.Builder builder = new Parameters.Builder();
        queryMap.forEach(builder::withParam);
        return getClient().get(builder.build());
    }

    @Override
    public Mono<Set<Character>> findSetCharacter(Set<UUID> uuid) {
        return getClient().get(Parameters.builder().withParam(ServiceConstants.UUID, uuid).build());
    }

    @Override
    public Mono<Set<Character>> updateSetCharacter(Set<Character> character) {
        return getClient().put(Parameters.builder().build(), character);
    }

}
