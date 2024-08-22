package com.firmys.gameservices.denizen.services;

import static com.firmys.gameservices.common.FunctionUtils.safeSet;

import com.firmys.gameservices.denizen.data.PlayerRepository;
import com.firmys.gameservices.generated.models.Inventory;
import com.firmys.gameservices.generated.models.Player;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
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
public class PlayerService extends GameService<Player> {
  private final GameServiceClient client;
  private final PlayerRepository repository;

  private final StatService statService;
  private final RaceService raceService;
  private final SkillService skillService;
  private final EffectService effectService;
  private final AttributeService attributeService;
  private final ProfessionService professionService;

  private final Class<Player> entityType = Player.class;

  @Transactional
  public Mono<Player> create(Player object) {
    return create(Mono.just(object));
  }

  public Mono<Player> ensureValues(Mono<Player> object) {
    return object
        .flatMap(obj -> resolveStats().apply(obj))
        .flatMap(obj -> resolveSkills().apply(obj))
        .flatMap(obj -> resolveEffects().apply(obj))
        .flatMap(obj -> resolveAttributes().apply(obj))
        .flatMap(obj -> resolveRace().apply(obj))
        .flatMap(obj -> resolveInventory().apply(obj));
  }

  private Function<Player, Mono<Player>> resolveStats() {
    return object ->
        Flux.fromIterable(safeSet(object.stats()))
            .filter(Objects::nonNull)
            .filter(value -> value.stat().id() == null)
            .flatMap(
                value -> {
                  if (value.stat().id() != null) {
                    return statService.get(value.stat().id()).map(value::withStat);
                  }
                  return statService
                      .findAllLike(value.stat())
                      .filter(obj -> obj.name().equals(value.stat().name()))
                      .singleOrEmpty()
                      .map(value::withStat)
                      .switchIfEmpty(statService.create(value.stat()).map(value::withStat));
                })
            .collectList()
            .map(list -> object.withStats(new HashSet<>(list)));
  }

  private Function<Player, Mono<Player>> resolveSkills() {
    return object ->
        Flux.fromIterable(safeSet(object.skills()))
            .filter(Objects::nonNull)
            .filter(value -> value.skill().id() == null)
            .flatMap(
                value -> {
                  if (value.skill().id() != null) {
                    return skillService.get(value.skill().id()).map(value::withSkill);
                  }
                  return skillService
                      .findAllLike(value.skill())
                      .filter(obj -> obj.name().equals(value.skill().name()))
                      .singleOrEmpty()
                      .map(value::withSkill)
                      .switchIfEmpty(skillService.create(value.skill()).map(value::withSkill));
                })
            .collectList()
            .map(list -> object.withSkills(safeSet(list)));
  }

  private Function<Player, Mono<Player>> resolveAttributes() {
    return object ->
        Flux.fromIterable(safeSet(object.attributes()))
            .filter(Objects::nonNull)
            .filter(value -> value.attribute().id() == null)
            .flatMap(
                value -> {
                  if (value.attribute().id() != null) {
                    return attributeService.get(value.attribute().id()).map(value::withAttribute);
                  }
                  return attributeService
                      .findAllLike(value.attribute())
                      .filter(obj -> obj.name().equals(value.attribute().name()))
                      .singleOrEmpty()
                      .map(value::withAttribute)
                      .switchIfEmpty(
                          attributeService.create(value.attribute()).map(value::withAttribute));
                })
            .collectList()
            .map(list -> object.withAttributes(new HashSet<>(list)));
  }

  private Function<Player, Mono<Player>> resolveEffects() {
    return object ->
        Flux.fromIterable(safeSet(object.effects()))
            .filter(Objects::nonNull)
            .filter(value -> value.effect().id() == null)
            .flatMap(
                value -> {
                  if (value.effect().id() != null) {
                    return effectService.get(value.effect().id()).map(value::withEffect);
                  }
                  return effectService
                      .findAllLike(value.effect())
                      .filter(obj -> obj.name().equals(value.effect().name()))
                      .singleOrEmpty()
                      .map(value::withEffect)
                      .switchIfEmpty(effectService.create(value.effect()).map(value::withEffect));
                })
            .collectList()
            .map(list -> object.withEffects(new HashSet<>(list)));
  }

  private Function<Player, Mono<Player>> resolveRace() {
    return object ->
        raceService
            .findAllLike(object.race())
            .filter(obj -> obj.name().equals(object.race().name()))
            .singleOrEmpty()
            .map(object::withRace)
            .switchIfEmpty(raceService.create(object.race()).map(object::withRace));
  }

  private Function<Player, Mono<Player>> resolveInventory() {
    return object ->
        Optional.of(object)
            .filter(obj -> obj.inventory() != null)
            .filter(obj -> obj.inventory().id() != null)
            .map(Mono::just)
            .orElseGet(() -> client.create(Inventory.builder().build()).map(object::withInventory));
  }
}
