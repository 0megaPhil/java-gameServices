package com.firmys.gameservices.app.services;

import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.GatewayClient;
import com.firmys.gameservices.common.JsonUtils;
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
public class QueryService {
  private final GatewayClient client;

  public <E extends CommonEntity> Mono<E> getById(UUID uuid, Class<E> entityClass) {
    return client.get(uuid, entityClass);
  }

  public <E extends CommonEntity> Flux<E> getAll(Integer limit, Class<E> entityClass) {
    return client.getAll(limit, entityClass);
  }

  public <E extends CommonEntity> Mono<E> create(String json, Class<E> entityClass) {
    return Mono.just(json)
        .map(str -> JsonUtils.fromJson(json, entityClass))
        .flatMap(client::create);
  }

  public <E extends CommonEntity> Mono<E> update(String json, Class<E> entityClass) {
    return Mono.just(json)
        .map(str -> JsonUtils.fromJson(json, entityClass))
        .flatMap(client::update);
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return client.update(object);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return client.create(object);
  }

  public Flux<Character> charactersByName(String name) {
    return client.getAll(1000, Character.class).filter(ch -> ch.name().contains(name));
  }
}
