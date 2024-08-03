package com.firmys.gameservices.characters.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.firmys.gameservices.characters.enums.Sexes;
import com.firmys.gameservices.characters.services.CharacterSkillService;
import com.firmys.gameservices.characters.services.CharacterStatService;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.attributes.Dimension;
import jakarta.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;
import javax.measure.quantity.Length;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Time;
import lombok.Builder;
import lombok.Singular;
import lombok.With;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import reactor.core.publisher.Flux;

@With
@Builder(toBuilder = true)
public record Character(
    @With @Id UUID uuid,
    @NotEmpty String name,
    String summary,
    String appearance,
    String personality,
    String background,
    Sexes sex,
    UUID raceId,
    Dimension<? extends Time> age,
    Dimension<? extends Length> height,
    Dimension<? extends Mass> weight,
    UUID userId,
    UUID inventoryId,
    @Singular Set<UUID> skillIds,
    @Singular Set<UUID> statIds,
    @ReadOnlyProperty @Singular Set<CharacterSkill> skills,
    @ReadOnlyProperty @Singular Set<CharacterStat> stats)
    implements CommonEntity {

  @JsonGetter("skills")
  public Flux<CharacterSkill> characterSkills(CharacterSkillService service) {
    return service.find(Flux.fromIterable(skillIds));
  }

  @JsonGetter("stats")
  public Flux<CharacterStat> characterStats(CharacterStatService service) {
    return service.find(Flux.fromIterable(statIds));
  }
}
