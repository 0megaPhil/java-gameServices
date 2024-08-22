package com.firmys.gameservices.service;

import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.common.Services;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Flavor;
import com.firmys.gameservices.generated.models.Operations;
import com.firmys.gameservices.generated.models.Options;
import com.firmys.gameservices.service.error.ErrorUtils;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Builder
@Component
@Import(JsonUtils.class)
@SuppressWarnings("unchecked")
public class GameServiceClient {

  @Builder.Default private final Map<Services, WebClient> restClients = new ConcurrentHashMap<>();

  @Builder.Default
  private final Map<Services, WebClient> graphQlClients = new ConcurrentHashMap<>();

  private final JsonUtils json;

  public <E extends CommonEntity> Mono<E> get(ObjectId id, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toUpperCase());
    return restClients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/" + id).build())
        .retrieve()
        .bodyToMono(entityClass)
        .map(obj -> obj);
  }

  // @TODO: Finish filtering and sorting for graphql queries
  public <E extends CommonEntity> Flux<E> find(Options options, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toUpperCase());
    LinkedMultiValueMap<String, String> queryParams = JSON.toMap(options);
    return restClients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.queryParams(queryParams).build())
        .retrieve()
        .bodyToFlux(entityClass)
        .map(obj -> obj);
  }

  public <E extends CommonEntity> Flux<E> find(E likeObject, Options options) {
    Services service = Services.byEntityName(likeObject.getClass().getSimpleName().toUpperCase());
    LinkedMultiValueMap<String, String> queryParams = JSON.toMap(likeObject);
    queryParams.addAll(JSON.toMap(options));
    return restClients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.queryParams(queryParams).build())
        .retrieve()
        .bodyToFlux(likeObject.getClass())
        .map(obj -> (E) obj);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return save(object, Operations.CREATE_ONE);
  }

  public <E extends CommonEntity> Mono<E> flavor(ObjectId id, Class<E> entityClass) {
    return get(id, entityClass)
        .flatMap(
            object ->
                restClients
                    .get(Services.FLAVOR)
                    .post()
                    .uri(
                        uriBuilder ->
                            uriBuilder
                                .path("/" + entityClass.getSimpleName().toLowerCase())
                                .build())
                    .body(Mono.just(object), object.getClass())
                    .retrieve()
                    .bodyToMono(String.class)
                    .map(str -> JSON.fromJson(str, Flavor.class))
                    .map(flavor -> (E) object.withFlavor(flavor)));
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return this.save(object, Operations.UPDATE_ONE);
  }

  public <E extends CommonEntity> Mono<Boolean> delete(ObjectId id, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toLowerCase());
    return restClients
        .get(service)
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/" + id).build())
        .retrieve()
        .bodyToMono(Boolean.class);
  }

  private <E extends CommonEntity> Mono<E> save(E object, Operations operation) {
    Services service = Services.byEntityName(object.getClass().getSimpleName().toLowerCase());
    if (operation.name().contains("CREATE")) {
      return (Mono<E>)
          restClients
              .get(service)
              .post()
              .body(BodyInserters.fromValue(object))
              .retrieve()
              .bodyToMono(object.getClass())
              .flatMap(
                  res ->
                      Optional.of(res)
                          .filter(r -> r.error() == null)
                          .map(Mono::just)
                          .orElseGet(() -> Mono.error(ErrorUtils.toException(res.error()))));
    }
    return (Mono<E>)
        restClients
            .get(service)
            .put()
            .body(BodyInserters.fromValue(object))
            .retrieve()
            .bodyToMono(object.getClass())
            .flatMap(
                res ->
                    Optional.of(res)
                        .filter(r -> r.error() == null)
                        .map(Mono::just)
                        .orElseGet(() -> Mono.error(ErrorUtils.toException(res.error()))));
  }

  private Options validOptions(Options options) {
    return Optional.ofNullable(options)
        .orElseGet(
            () -> Options.builder().limit(1000).filters(new HashSet<>()).sortBy("id").build());
  }
}
