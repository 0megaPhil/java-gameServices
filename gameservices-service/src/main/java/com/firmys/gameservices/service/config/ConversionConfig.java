package com.firmys.gameservices.service.config;

import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.firmys.gameservices.service.data.CommonConverters;
import graphql.scalars.ExtendedScalars;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(CommonConverters.class)
public class ConversionConfig {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder ->
        wiringBuilder.scalar(ExtendedScalars.Json).scalar(ExtendedScalars.DateTime);
  }

  @Bean
  public JsonOrgModule jsonOrgModule() {
    return new JsonOrgModule();
  }
}
