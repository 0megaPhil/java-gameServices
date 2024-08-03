package com.firmys.gameservices.characters.services;

import com.firmys.gameservices.characters.data.CharacterSkillRepository;
import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.characters.models.CharacterSkill;
import com.firmys.gameservices.characters.models.Skill;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.GatewayClient;
import java.util.Optional;
import java.util.Set;
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
public class CharacterSkillService extends CommonService<CharacterSkill> {

  private final CharacterSkillRepository repository;
  private final GatewayClient gatewayClient;
  private final SkillService skillService;

  public Mono<Character> applyCharacterSkills(Mono<Character> object) {
    return object.flatMap(
        obj ->
            skillService
                .find(
                    Flux.fromStream(
                        Optional.ofNullable(obj.skills()).orElse(Set.of()).stream()
                            .filter(csk -> csk.uuid() == null)
                            .map(CharacterSkill::skillId)))
                .flatMap(s -> create(obj, s))
                .map(CharacterSkill::uuid)
                .collectList()
                .map(list -> obj.toBuilder().skillIds(list).build()));
  }

  private Mono<CharacterSkill> create(Character object, Skill skill) {
    return this.create(
        Mono.just(
            CharacterSkill.builder()
                .name(skill.name())
                .skillId(skill.uuid())
                .skillValue(
                    Optional.ofNullable(object.skills()).orElse(Set.of()).stream()
                        .filter(sk -> sk.skillId().equals(skill.uuid()))
                        .findFirst()
                        .orElseThrow()
                        .skillValue())
                .build()));
  }
}
