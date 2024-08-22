package com.firmys.gameservices.data.config;

import graphql.kickstart.tools.GraphQLResolver;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataConfig {
  @Bean
  public Map<String, GraphQLResolver<Void>> graphQLResolvers(
      List<GraphQLResolver<Void>> graphQLResolvers) {
    return graphQLResolvers.stream()
        .collect(Collectors.toMap(res -> res.getClass().getSimpleName(), res -> res));
  }
}
