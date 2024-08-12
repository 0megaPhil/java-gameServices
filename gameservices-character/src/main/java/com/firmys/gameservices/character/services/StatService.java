package com.firmys.gameservices.character.services;

import com.firmys.gameservices.character.data.StatRepository;
import com.firmys.gameservices.common.CommonService;
import com.firmys.gameservices.common.ServiceClient;
import com.firmys.gameservices.generated.models.Stat;
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
public class StatService extends CommonService<Stat> {
  private final StatRepository repository;
  private final ServiceClient serviceClient;

  private final Class<Stat> entityType = Stat.class;
}
