package com.firmys.gameservices.common.config;

import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
import com.firmys.gameservices.common.data.CommonConverters;
import com.firmys.gameservices.common.data.CommonEntityConverters;
import com.firmys.gameservices.generated.models.CommonObject;
import graphql.scalars.ExtendedScalars;
import io.r2dbc.spi.ConnectionFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.GenericConverter.ConvertiblePair;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.DialectResolver;
import org.springframework.data.util.Pair;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Import(CommonConverters.class)
public class ConversionConfig {

  @Bean
  public Pair<Set<ConvertiblePair>, Set<ConvertiblePair>> readWritConvertablePairs() {
    Reflections reflections = new Reflections("com.firmys.gameservices");
    Set<ConvertiblePair> readPairs = new HashSet<>();
    Set<ConvertiblePair> writePairs = new HashSet<>();
    reflections
        .getSubTypesOf(CommonObject.class)
        .forEach(
            cls -> {
              writePairs.add(new ConvertiblePair(cls, String.class));
              readPairs.add(new ConvertiblePair(String.class, cls));
            });
    reflections
        .getSubTypesOf(Enum.class)
        .forEach(
            cls -> {
              writePairs.add(new ConvertiblePair(cls, String.class));
              readPairs.add(new ConvertiblePair(String.class, cls));
            });
    return Pair.of(readPairs, writePairs);
  }

  //  @Bean
  @Primary
  public <E extends CommonObject> R2dbcCustomConversions r2dbcCustomConversions(
      Pair<Set<ConvertiblePair>, Set<ConvertiblePair>> readWritConvertablePairs,
      ConnectionFactory connectionFactory) {

    //    Set<ConvertiblePair> commonObjectRead =
    //        readWritConvertablePairs.getFirst().stream()
    //            .filter(pair -> CommonObject.class.isAssignableFrom(pair.getTargetType()))
    //            .collect(Collectors.toSet());
    //    Set<ConvertiblePair> commonObjectWrite =
    //        readWritConvertablePairs.getSecond().stream()
    //            .filter(pair -> CommonObject.class.isAssignableFrom(pair.getSourceType()))
    //            .collect(Collectors.toSet());

    Set<ConvertiblePair> enumRead =
        readWritConvertablePairs.getFirst().stream()
            .filter(pair -> Enum.class.isAssignableFrom(pair.getTargetType()))
            .collect(Collectors.toSet());
    Set<ConvertiblePair> enumWrite =
        readWritConvertablePairs.getSecond().stream()
            .filter(pair -> Enum.class.isAssignableFrom(pair.getSourceType()))
            .collect(Collectors.toSet());

    List<Object> converters = new ArrayList<>();
    //    commonObjectRead.forEach(
    //        pair -> new CommonEntityConverters.CommonSetReader<>((Class<E>)
    // pair.getTargetType()));
    //    converters.add(new CommonEntityConverters.CommonSetReader());
    //    converters.add(new CommonEntityConverters.CommonSetWriter());
    converters.add(new CommonEntityConverters.LocalTimeReader());
    converters.add(new CommonEntityConverters.LocalTimeWriter());
    //    converters.add(new CommonEntityConverters.EntityReadConverter(commonObjectRead));
    //    converters.add(new CommonEntityConverters.EntityWriteConverter(commonObjectWrite));
    converters.add(new CommonEntityConverters.EnumReadConverter(enumRead));
    converters.add(new CommonEntityConverters.EnumWriteConverter(enumWrite));
    return R2dbcCustomConversions.of(DialectResolver.getDialect(connectionFactory), converters);
  }

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
