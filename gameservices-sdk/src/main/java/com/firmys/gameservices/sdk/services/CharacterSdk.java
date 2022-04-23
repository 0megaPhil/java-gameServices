package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CharacterApi;
import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.sdk.client.GatewayClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CharacterSdk extends AbstractSdk implements CharacterApi {
    private final ParameterizedTypeReference<Character> typeReference = new ParameterizedTypeReference<>() {};
    private final ParameterizedTypeReference<Void> voidTypeReference = new ParameterizedTypeReference<>() {};

    public CharacterSdk(GatewayClient client) {
        super(client, ServicePaths.CHARACTER_PATH);
    }

    @Override
    public Mono<Character> addCharacter(Character character) {
        return null;
    }

    @Override
    public Mono<Void> deleteByUuidCharacter(String uuid) {
        return null;
    }

    @Override
    public Mono<Void> deleteCharacter(String uuid, Character character) {
        return null;
    }

    @Override
    public Mono<Character> findByUuidParamCharacter(String uuid) {
        return null;
    }

    @Override
    public Mono<Character> findByUuidPathCharacter(String uuid) {
        return null;
    }

    @Override
    public Mono<Character> updateByUuidCharacter(String uuid, Character character) {
        return null;
    }

    @Override
    public Mono<Character> updateCharacter(Character character) {
        return null;
    }
}
