package com.firmys.gameservices.common;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Builder
@Component
@SuppressWarnings("unchecked")
public class GatewayClient {

  @Builder.Default private final Map<Services, WebClient> clients = new ConcurrentHashMap<>();

  public <E extends CommonEntity> Mono<E> get(Class<E> entityType, UUID uuid) {
    return clients
        .get(Services.valueOf(entityType.getSimpleName().toUpperCase()))
        .get()
        .uri(uriBuilder -> uriBuilder.queryParam(CommonConstants.UUID, uuid).build())
        .retrieve()
        .bodyToMono(entityType);
  }

  public <E extends CommonEntity> Flux<E> getAll(Integer limit, Class<E> entityType) {
    return clients
        .get(Services.valueOf(entityType.getSimpleName().toUpperCase()))
        .get()
        .uri(uriBuilder -> uriBuilder.queryParam(CommonConstants.LIMIT, limit).build())
        .retrieve()
        .bodyToFlux(entityType);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return Optional.ofNullable(object)
        .filter(obj -> obj.uuid() == null)
        .map(this::save)
        .orElseThrow();
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return Optional.ofNullable(object)
        .filter(obj -> obj.uuid() != null)
        .map(this::save)
        .orElseThrow();
  }

  public Mono<Void> delete(Services service, UUID uuid) {
    return clients
        .get(service)
        .delete()
        .uri(uriBuilder -> uriBuilder.queryParam(CommonConstants.UUID, uuid).build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  private <E extends CommonEntity> Mono<E> save(E object) {
    String className = object.getClass().getSimpleName().toLowerCase();
    Services service =
        Arrays.stream(Services.values())
            .filter(s -> s.getName().equals(className))
            .findFirst()
            .orElseThrow();
    return (Mono<E>)
        clients
            .get(service)
            .post()
            .body(Mono.just(object), object.getClass())
            .retrieve()
            .bodyToMono(object.getClass());
  }
}
