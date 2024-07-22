package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.inventory.models.Inventory;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterService extends CommonService<Character> {
  private final CharacterRepository repository;
  private final GatewayClient gatewayClient;

  @Override
  public Mono<Character> create(Mono<Character> object) {
    return super.create(object).flatMap(this::generateInventory);
  }

  public Mono<Character> generateInventory(Character character) {
    return gatewayClient
        .create(Inventory.builder().characterId(character.uuid()).build())
        .flatMap(inv -> super.update(character.toBuilder().inventoryId(inv.uuid()).build()));
  }
}
