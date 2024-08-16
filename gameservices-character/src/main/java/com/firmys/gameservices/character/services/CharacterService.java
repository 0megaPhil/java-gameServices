package com.firmys.gameservices.character.services;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;

import com.firmys.gameservices.character.data.CharacterRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.ServiceClient;
import com.firmys.gameservices.generated.models.Character;
import com.firmys.gameservices.generated.models.Inventory;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterService extends CommonService<Character> {
  private final ServiceClient client;
  private final CharacterRepository repository;

  private final StatService statService;
  private final RaceService raceService;
  private final SkillService skillService;
  private final EffectService effectService;
  private final AttributeService attributeService;
  private final ProfessionService professionService;

  private final Class<Character> entityType = Character.class;

  @Transactional
  public Mono<Character> create(Character object) {
    return create(Mono.just(object));
  }

  public Mono<Character> ensureValues(Mono<Character> character) {
    return character
        .flatMap(object -> resolveStats().apply(object))
        .flatMap(object -> resolveSkills().apply(object))
        .flatMap(object -> resolveEffects().apply(object))
        .flatMap(object -> resolveAttributes().apply(object))
        .flatMap(object -> resolveRace().apply(object))
        .flatMap(object -> resolveInventory().apply(object));
  }

  private Function<Character, Mono<Character>> resolveStats() {
    return object ->
        Flux.fromIterable(safeSet(object.stats()))
            .filter(Objects::nonNull)
            .filter(value -> value.stat().id() == null)
            .flatMap(
                value -> {
                  if (value.stat().id() != null) {
                    return statService.find(value.stat().id()).map(value::withStat);
                  }
                  return statService
                      .findOneLike(value.stat())
                      .map(value::withStat)
                      .switchIfEmpty(statService.create(value.stat()).map(value::withStat));
                })
            .collectList()
            .map(list -> object.withStats(new HashSet<>(list)));
  }

  private Function<Character, Mono<Character>> resolveSkills() {
    return object ->
        Flux.fromIterable(safeSet(object.skills()))
            .filter(Objects::nonNull)
            .filter(value -> value.skill().id() == null)
            .flatMap(
                value -> {
                  if (value.skill().id() != null) {
                    return skillService.find(value.skill().id()).map(value::withSkill);
                  }
                  return skillService
                      .findOneLike(value.skill())
                      .map(value::withSkill)
                      .switchIfEmpty(skillService.create(value.skill()).map(value::withSkill));
                })
            .collectList()
            .map(list -> object.withSkills(safeSet(list)));
  }

  private Function<Character, Mono<Character>> resolveAttributes() {
    return object ->
        Flux.fromIterable(safeSet(object.attributes()))
            .filter(Objects::nonNull)
            .filter(value -> value.attribute().id() == null)
            .flatMap(
                value -> {
                  if (value.attribute().id() != null) {
                    return attributeService.find(value.attribute().id()).map(value::withAttribute);
                  }
                  return attributeService
                      .findOneLike(value.attribute())
                      .map(value::withAttribute)
                      .switchIfEmpty(
                          attributeService.create(value.attribute()).map(value::withAttribute));
                })
            .collectList()
            .map(list -> object.withAttributes(new HashSet<>(list)));
  }

  private Function<Character, Mono<Character>> resolveEffects() {
    return object ->
        Flux.fromIterable(safeSet(object.effects()))
            .filter(Objects::nonNull)
            .filter(value -> value.effect().id() == null)
            .flatMap(
                value -> {
                  if (value.effect().id() != null) {
                    return effectService.find(value.effect().id()).map(value::withEffect);
                  }
                  return effectService
                      .findOneLike(value.effect())
                      .map(value::withEffect)
                      .switchIfEmpty(effectService.create(value.effect()).map(value::withEffect));
                })
            .collectList()
            .map(list -> object.withEffects(new HashSet<>(list)));
  }

  private Function<Character, Mono<Character>> resolveRace() {
    return object -> {
      if (object.race().id() != null) {
        return raceService.find(object.race().id()).map(object::withRace);
      }
      return raceService
          .findOneLike(object.race())
          .map(object::withRace)
          .switchIfEmpty(raceService.create(object.race()).map(object::withRace));
    };
  }

  private Function<Character, Mono<Character>> resolveInventory() {
    return object ->
        Optional.of(object)
            .filter(obj -> obj.inventory() != null)
            .filter(obj -> obj.inventory().id() != null)
            .map(Mono::just)
            .orElseGet(() -> client.create(Inventory.builder().build()).map(object::withInventory));
  }
}
