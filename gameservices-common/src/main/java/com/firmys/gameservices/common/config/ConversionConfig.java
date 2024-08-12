package com.firmys.gameservices.common.config;

import com.firmys.gameservices.common.data.CommonEntityConverters;
import com.firmys.gameservices.generated.models.CommonObject;
import graphql.scalars.ExtendedScalars;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Slf4j
@Configuration
public class ConversionConfig {

  @Bean
  @Primary
  public R2dbcCustomConversions r2dbcCustomConversions(ConnectionFactory connectionFactory) {
    List<Object> converters = new ArrayList<>();
    Reflections reflections = new Reflections("com.firmys.gameservices");
    Set<Class<? extends CommonObject>> classes = reflections.getSubTypesOf(CommonObject.class);
    Set<ConvertiblePair> writePairs =
        classes.stream()
            .map(cls -> new ConvertiblePair(cls, String.class))
            .collect(Collectors.toSet());

    Set<ConvertiblePair> readPairs =
        classes.stream()
            .map(cls -> new ConvertiblePair(String.class, cls))
            .collect(Collectors.toSet());

    converters.add(new CommonEntityConverters.EntityReadConverter(readPairs));
    converters.add(new CommonEntityConverters.EntityWriteConverter(writePairs));
    return R2dbcCustomConversions.of(DialectResolver.getDialect(connectionFactory), converters);
  }

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    return wiringBuilder ->
        wiringBuilder
            .scalar(ExtendedScalars.Json)
            .scalar(ExtendedScalars.DateTime)
            .scalar(ExtendedScalars.UUID);
  }
}
