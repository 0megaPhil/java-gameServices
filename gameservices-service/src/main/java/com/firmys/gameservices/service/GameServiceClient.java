package com.firmys.gameservices.service;

import com.firmys.gameservices.common.CommonConstants;
import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.common.Services;
import com.firmys.gameservices.generated.models.CommonEntity;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.generated.models.Flavor;
import com.firmys.gameservices.generated.models.Options;
import com.firmys.gameservices.service.error.ServiceException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatusCode;
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

  @Builder.Default private final Map<Services, WebClient> clients = new ConcurrentHashMap<>();
  private final JsonUtils json;

  public <E extends CommonEntity> Mono<E> get(ObjectId id, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toUpperCase());
    return clients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.path("/" + id).build())
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
        .onStatus(
            HttpStatusCode::isError,
            response -> {
              return response
                  .bodyToMono(String.class)
                  .flatMap(
                      str -> Mono.error(new ServiceException(Error.builder().data(str).build())));
            })
        .bodyToFlux(entityClass);
  }

  public <E extends CommonEntity> Flux<E> find(E likeObject, Options options) {
    Services service = Services.byEntityName(likeObject.getClass().getSimpleName().toUpperCase());
    LinkedMultiValueMap<String, String> queryParams = JsonUtils.JSON.toMap(likeObject);
    queryParams.addAll(JsonUtils.JSON.toMap(options));
    return clients
        .get(service)
        .get()
        .uri(uriBuilder -> uriBuilder.queryParams(queryParams).build())
        .retrieve()
        .bodyToFlux(likeObject.getClass())
        .map(obj -> (E) obj);
  }

  public <E extends CommonEntity> Mono<E> create(E object) {
    return Optional.ofNullable(object)
        .filter(obj -> obj.id() == null)
        .map(this::save)
        .orElseThrow();
  }

  public <E extends CommonEntity> Mono<Flavor> flavor(E object) {
    String className = object.getClass().getSimpleName().toLowerCase();
    return clients
        .get(Services.FLAVOR)
        .post()
        .uri(uriBuilder -> uriBuilder.path("/" + className).build())
        .body(Mono.just(object), object.getClass())
        .retrieve()
        .bodyToMono(String.class)
        .map(str -> JsonUtils.JSON.fromJson(str, Flavor.class));
  }

  public <E extends CommonEntity> Mono<E> update(E object) {
    return Mono.just(object)
        .filter(obj -> obj.id() != null)
        .switchIfEmpty(find(object, validOptions(null)).single())
        .flatMap(obj -> this.save(object));
  }

  public <E extends CommonEntity> Mono<Void> delete(ObjectId id, Class<E> entityClass) {
    Services service = Services.byEntityName(entityClass.getSimpleName().toLowerCase());
    return clients
        .get(service)
        .delete()
        .uri(uriBuilder -> uriBuilder.path("/" + id).build())
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
            .onStatus(
                HttpStatusCode::isError,
                response -> {
                  return response
                      .bodyToMono(String.class)
                      .flatMap(
                          str ->
                              Mono.error(
                                  new ServiceException(
                                      Error.builder().data(object.toJson()).build())));
                })
            .bodyToMono(object.getClass());
  }

  private Options validOptions(Options options) {
    return Optional.ofNullable(options)
        .orElseGet(
            () -> Options.builder().limit(1000).filters(new HashSet<>()).sortBy("id").build());
  }
}
