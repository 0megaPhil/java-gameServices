package com.firmys.gameservices.gateway.services;

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
public class GatewayService {
  private final GatewayClient client;

  public <E extends CommonEntity> Mono<E> getById(UUID uuid, Class<E> entityType) {
    return client.get(entityType, uuid);
  }

  public <E extends CommonEntity> Flux<E> getAll(Integer limit, Class<E> entityType) {
    return client.getAll(limit, entityType);
  }

  public <E extends CommonEntity> Mono<E> create(String json, Class<E> entityType) {
    return Mono.just(json).map(str -> JsonUtils.fromJson(json, entityType)).flatMap(client::create);
  }

  public <E extends CommonEntity> Mono<E> update(String json, Class<E> entityType) {
    return Mono.just(json).map(str -> JsonUtils.fromJson(json, entityType)).flatMap(client::update);
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
