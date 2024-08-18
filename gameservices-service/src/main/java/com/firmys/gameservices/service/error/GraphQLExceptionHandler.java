package com.firmys.gameservices.service.error;

import org.jetbrains.annotations.NotNull;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GraphQLExceptionHandler implements WebGraphQlInterceptor {

  @Override
  public @NotNull Mono<WebGraphQlResponse> intercept(
      @NotNull WebGraphQlRequest request, @NotNull Chain chain) {
    return chain.next(request).map(res -> res);
  }
}
