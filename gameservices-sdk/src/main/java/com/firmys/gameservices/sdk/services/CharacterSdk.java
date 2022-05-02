package com.firmys.gameservices.sdk.services;

import com.firmys.gameservices.api.CharacterApi;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.models.Character;
import com.firmys.gameservices.sdk.Parameters;
import com.firmys.gameservices.sdk.gateway.GatewayDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CharacterSdk extends AbstractSdk<Character> implements CharacterApi {

    public CharacterSdk(GatewayDetails gatewayDetails) {
        super(gatewayDetails, ServiceConstants.CHARACTER_PATH, new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Mono<Character> createCharacter(Character character) {
        return getClient().post(Parameters.builder().build(), character);
    }

    @Override
    public Mono<Void> deleteCharacter(UUID pathUuid) {
        return getClient().withPath(pathUuid).delete(Parameters.builder().build());
    }

    @Override
    public Mono<Character> findCharacter(UUID pathUuid) {
        return getClient().withPath(pathUuid).get(Parameters.builder().build());
    }

    @Override
    public Mono<Character> updateCharacter(UUID pathUuid, Character character) {
        return getClient().withPath(pathUuid).put(Parameters.builder().build(), character);
    }

}
