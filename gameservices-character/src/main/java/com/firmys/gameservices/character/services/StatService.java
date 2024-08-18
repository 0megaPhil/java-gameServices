package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.StatRepository;
import com.firmys.gameservices.generated.models.Stat;
import com.firmys.gameservices.service.GameService;
import com.firmys.gameservices.service.GameServiceClient;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class StatService extends GameService<Stat> {
  private final StatRepository repository;
  private final GameServiceClient gameServiceClient;

  private final Class<Stat> entityType = Stat.class;
}
