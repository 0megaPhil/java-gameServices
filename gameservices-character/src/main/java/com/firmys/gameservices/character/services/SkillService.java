package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.SkillRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.generated.models.Skill;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class SkillService extends CommonService<Skill> {
  private final SkillRepository repository;

  private final Class<Skill> entityType = Skill.class;
}
