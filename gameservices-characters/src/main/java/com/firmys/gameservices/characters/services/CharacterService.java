package com.firmys.gameservices.characters.services;

import static com.firmys.gameservices.common.FunctionUtils.safeList;

import com.firmys.gameservices.characters.data.CharacterRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.generated.models.Character;
import com.firmys.gameservices.generated.models.CharacterAttribute;
import com.firmys.gameservices.generated.models.CharacterEffect;
import com.firmys.gameservices.generated.models.CharacterSkill;
import com.firmys.gameservices.generated.models.CharacterStat;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.CommonValue;
import com.firmys.gameservices.generated.models.Inventory;
import java.util.HashSet;
import java.util.function.BiFunction;
import java.util.function.Function;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class CharacterService extends CommonService<Character> {
  private final CharacterRepository repository;
  private final SkillService skillService;
  private final StatService statService;
  private final AttributeService attributeService;
  private final EffectService effectService;
  private final GatewayClient gatewayClient;

  @Override
  public Mono<Character> create(Mono<Character> object) {
    return super.create(ensureValues(object)).flatMap(this::generateInventory);
  }

  public Mono<Character> generateInventory(Character character) {
    return gatewayClient
        .create(Inventory.builder().build())
        .flatMap(inv -> super.update(character.toBuilder().inventory(inv).build()));
  }

  private Mono<Character> ensureValues(Mono<Character> character) {
    return character
        .flatMap(object -> applyStats().apply(object))
        .flatMap(object -> applySkills().apply(object))
        .flatMap(object -> applyEffects().apply(object))
        .flatMap(object -> applyAttributes().apply(object));
  }

  private Function<Character, Mono<Character>> applyStats() {
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

  private Function<Character, Mono<Character>> applySkills() {
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

  private Function<Character, Mono<Character>> applyAttributes() {
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

  private Function<Character, Mono<Character>> applyEffects() {
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

  private <E extends CommonEntity, V extends CommonValue> Flux<V> applyValues(
      Flux<Pair<E, Number>> toFindOr, CommonService<E> service, BiFunction<E, Number, V> mapper) {
    return toFindOr.flatMap(
        pair -> service.findOrCreate(pair.getFirst()).map(o -> mapper.apply(o, pair.getSecond())));
  }
}
