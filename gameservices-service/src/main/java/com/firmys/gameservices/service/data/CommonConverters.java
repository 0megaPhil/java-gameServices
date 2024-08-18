package com.firmys.gameservices.service.data;

import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.generated.models.CommonObject;
import jakarta.annotation.Nonnull;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Configuration
public class CommonConverters {

  @RequiredArgsConstructor
  public static class CommonConverter<E extends CommonObject> implements Converter<String, E> {
    private final Class<E> entityType;

    @Override
    public E convert(@Nonnull String source) {
      return JsonUtils.JSON.fromJson(source, entityType);
    }
  }

  @Component
  public static class OffsetDateTimeConverter implements Converter<OffsetDateTime, Instant> {

    @Override
    public Instant convert(@Nonnull OffsetDateTime source) {
      return source.toInstant();
    }
  }

  @Component
  public static class InstantConverter implements Converter<Instant, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(@Nonnull Instant source) {
      return source.atOffset(ZoneOffset.UTC);
    }
  }
}
