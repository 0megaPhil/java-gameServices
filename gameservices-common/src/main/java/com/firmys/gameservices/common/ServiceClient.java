package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.JsonUtils.JSON;
import static com.firmys.gameservices.common.Services.FLAVOR;

import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Flavor;
import com.firmys.gameservices.generated.models.Options;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Builder
@Component
@Import(JsonUtils.class)
@SuppressWarnings("unchecked")
public class ServiceClient {

  @Builder.Default private final Map<Services, WebClient> clients = new ConcurrentHashMap<>();
  private final JsonUtils json;

  public <E extends CommonEntity> Mono<E> get(UUID uuid, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toUpperCase());
    return clients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/" + uuid).build())
        .retrieve()
        .bodyToMono(entityClass);
  }

  // @TODO: Finish filtering and sorting for graphql queries
  public <E extends CommonEntity> Flux<E> find(Options options, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toUpperCase());
    return clients
        .get(service)
        .get()
        .uri(
            uriBuilder ->
                uriBuilder.queryParam(CommonConstants.LIMIT, validOptions(options).limit()).build())
        .retrieve()
        .bodyToFlux(entityClass);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return Optional.ofNullable(object)
        .filter(obj -> obj.uuid() == null)
        .map(this::save)
        .orElseThrow();
  }

  public <E extends CommonEntity> Mono<Flavor> flavor(E object) {
    String className = object.getClass().getSimpleName().toLowerCase();
    return clients
        .get(FLAVOR)
        .post()
        .uri(uriBuilder -> uriBuilder.path("/" + className).build())
        .body(Mono.just(object), object.getClass())
        .retrieve()
        .bodyToMono(String.class)
        .map(str -> JSON.fromJson(str, Flavor.class));
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return Optional.ofNullable(object)
        .filter(obj -> obj.uuid() != null)
        .map(this::save)
        .orElseThrow();
  }

  public <E extends CommonEntity> Mono<Void> delete(UUID uuid, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toLowerCase());
    return clients
        .get(service)
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/" + uuid).build())
        .retrieve()
        .bodyToMono(Void.class);
  }

  private <E extends CommonEntity> Mono<E> save(E object) {
    Services service = Services.byEntityName(object.getClass().getSimpleName().toLowerCase());
    return (Mono<E>)
        clients
            .get(service)
            .post()
            .body(BodyInserters.fromValue(object))
            .retrieve()
            .bodyToMono(object.getClass());
  }

  private Options validOptions(Options options) {
    return Optional.ofNullable(options)
        .orElseGet(() -> Options.builder().limit(1000).filters(Set.of()).sortBy("uuid").build());
  }
}
