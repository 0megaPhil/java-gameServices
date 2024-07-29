package com.firmys.gameservices.app.services;

import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.common.data.Flavor;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@Builder(toBuilder = true)
@Accessors(chain = true, fluent = true)
public class FlavorService {
  private final GatewayClient client;

  public <E extends CommonEntity> Mono<Flavor> flavor(UUID uuid, Class<E> entityClass) {
    return client.get(uuid, entityClass).flatMap(client::flavor);
  }
}
