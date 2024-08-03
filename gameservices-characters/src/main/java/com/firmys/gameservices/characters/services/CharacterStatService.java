package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.data.CharacterStatRepository;
import com.firmys.gameservices.characters.models.*;
import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterStatService extends CommonService<CharacterStat> {
  private final CharacterStatRepository repository;
  private final GatewayClient gatewayClient;
  private final StatService statService;

  public Mono<Character> applyCharacterStats(Mono<Character> object) {
    return object.flatMap(
        obj ->
            statService
                .find(
                    Flux.fromStream(
                        obj.stats().stream()
                            .filter(csk -> csk.uuid() == null)
                            .map(CharacterStat::statId)))
                .flatMap(s -> create(obj, s))
                .map(CharacterStat::uuid)
                .collectList()
                .map(list -> obj.toBuilder().skillIds(list).build()));
  }

  private Mono<CharacterStat> create(Character object, Stat stat) {
    return this.create(
        Mono.just(
            CharacterStat.builder()
                .name(stat.name())
                .statId(stat.uuid())
                .statValue(
                    object.skills().stream()
                        .filter(sk -> sk.skillId().equals(stat.uuid()))
                        .findFirst()
                        .orElseThrow()
                        .skillValue())
                .build()));
  }
}
