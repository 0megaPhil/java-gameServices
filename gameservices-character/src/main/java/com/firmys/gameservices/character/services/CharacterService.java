package com.firmys.gameservices.character.services;

import static com.firmys.gameservices.common.FunctionUtils.safeList;

import com.firmys.gameservices.character.data.CharacterRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.ServiceClient;
import com.firmys.gameservices.generated.models.Character;
import com.firmys.gameservices.generated.models.CharacterAttribute;
import com.firmys.gameservices.generated.models.CharacterEffect;
import com.firmys.gameservices.generated.models.CharacterSkill;
import com.firmys.gameservices.generated.models.CharacterStat;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.CommonValue;
import com.firmys.gameservices.generated.models.Inventory;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.util.Pair;
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

  @Override
  public Mono<Character> create(Mono<Character> object) {
    return ensureValues(object).flatMap(super::create);
  }

  @Transactional
  public Mono<Character> create(Character object) {
    return create(Mono.just(object));
  }

  private Mono<Character> ensureValues(Mono<Character> character) {
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
        applyValues(
                Flux.fromStream(
                    safeList(object.stats()).stream().map(st -> Pair.of(st.stat(), st.value()))),
                statService,
                (obj, value) ->
                    CharacterStat.builder().stat(obj).value(value.doubleValue()).build())
            .collectList()
            .map(list -> object.toBuilder().stats(new HashSet<>(list)).build());
  }

  private Function<Character, Mono<Character>> resolveSkills() {
    return object ->
        applyValues(
                Flux.fromStream(
                    safeList(object.skills()).stream().map(st -> Pair.of(st.skill(), st.value()))),
                skillService,
                (obj, value) ->
                    CharacterSkill.builder().skill(obj).value(value.doubleValue()).build())
            .collectList()
            .map(list -> object.toBuilder().skills(new HashSet<>(list)).build());
  }

  private Function<Character, Mono<Character>> resolveAttributes() {
    return object ->
        applyValues(
                Flux.fromStream(
                    safeList(object.attributes()).stream()
                        .map(st -> Pair.of(st.attribute(), st.value()))),
                attributeService,
                (obj, value) ->
                    CharacterAttribute.builder().attribute(obj).value(value.doubleValue()).build())
            .collectList()
            .map(list -> object.toBuilder().attributes(new HashSet<>(list)).build());
  }

  private Function<Character, Mono<Character>> resolveEffects() {
    return object ->
        applyValues(
                Flux.fromStream(
                    safeList(object.effects()).stream()
                        .map(st -> Pair.of(st.effect(), st.value()))),
                effectService,
                (obj, value) ->
                    CharacterEffect.builder().effect(obj).value(value.doubleValue()).build())
            .collectList()
            .map(list -> object.toBuilder().effects(new HashSet<>(list)).build());
  }

  private Function<Character, Mono<Character>> resolveRace() {
    return object -> raceService.updateOrCreate(object.race()).map(object::withRace);
  }

  private <E extends CommonEntity, V extends CommonValue> Flux<V> applyValues(
      Flux<Pair<E, Number>> toFindOr, CommonService<E> service, BiFunction<E, Number, V> mapper) {
    return toFindOr.flatMap(
        pair ->
            service.updateOrCreate(pair.getFirst()).map(o -> mapper.apply(o, pair.getSecond())));
  }

  private Function<Character, Mono<Character>> resolveInventory() {
    return object ->
        Optional.of(object)
            .filter(obj -> obj.inventory() != null)
            .filter(obj -> obj.inventory().uuid() != null)
            .map(Mono::just)
            .orElseGet(() -> client.create(Inventory.builder().build()).map(object::withInventory));
  }
}
