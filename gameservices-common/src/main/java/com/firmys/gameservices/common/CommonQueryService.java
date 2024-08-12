package com.firmys.gameservices.common;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Options;
import java.util.UUID;
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
public class CommonQueryService {
  private final ServiceClient client;

  public <E extends CommonEntity> Mono<E> get(UUID uuid, Class<E> entityClass) {
    return client.get(uuid, entityClass);
  }

  public <E extends CommonEntity> Flux<E> find(Options options, Class<E> entityClass) {
    return client.find(options, entityClass);
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return client.update(object);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return client.create(object);
  }

  @SuppressWarnings("unchecked")
  public <E extends CommonEntity> Mono<E> flavor(UUID uuid, Class<E> entityClass) {
    return client
        .get(uuid, entityClass)
        .flatMap(obj -> client.flavor(obj).map(obj::withFlavor))
        .map(flavored -> (E) flavored);
  }
}
