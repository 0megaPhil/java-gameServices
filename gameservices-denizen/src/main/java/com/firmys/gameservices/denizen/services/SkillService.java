package com.firmys.gameservices.denizen.services;

import com.firmys.gameservices.denizen.data.SkillRepository;
import com.firmys.gameservices.generated.models.Skill;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class SkillService extends GameService<Skill> {
  private final SkillRepository repository;
  private final GameServiceClient gameServiceClient;
  private final Class<Skill> entityType = Skill.class;
}
