package com.firmys.gameservices.sdk.controllers;

import com.firmys.gameservices.api.CharactersApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class CharactersSdk extends AbstractSdk implements CharactersApi {
    private final ParameterizedTypeReference<Character> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Set<Character>> setTypeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public CharactersSdk(GatewayClient client) {
        super(client, ServicePaths.CHARACTERS_PATH);
    }

    @Override
    public Mono<Set<Character>> addMultipleCharacter(Set<Character> character) {
        return null;
    }

    @Override
    public Mono<Void> deleteMultipleCharacter(Set<String> uuid, Set<Character> character) {
        return null;
    }

    @Override
    public Mono<Set<Character>> findMultipleCharacter(Set<String> uuid) {
        return null;
    }

    @Override
    public Mono<Set<Character>> searchByAttributesCharacter(Set<String> attribute, String partial, String match) {
        return null;
    }

    @Override
    public Mono<Set<Character>> updateMultipleCharacter(Set<Character> character) {
        return null;
    }
}
