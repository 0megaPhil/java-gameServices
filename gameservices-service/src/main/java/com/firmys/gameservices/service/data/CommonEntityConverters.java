package com.firmys.gameservices.service.data;

import static java.time.ZoneOffset.UTC;

import com.firmys.gameservices.common.JsonUtils;
import com.firmys.gameservices.generated.models.CommonObject;
import com.firmys.gameservices.service.utils.ConversionUtils;
import jakarta.annotation.Nonnull;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

public class CommonEntityConverters {

  @WritingConverter
  @RequiredArgsConstructor
  public static class EntityWriteConverter implements GenericConverter {
    private final Set<ConvertiblePair> convertiblePairs;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return convertiblePairs;
    }

    @Override
    public String convert(
        Object source, @Nonnull TypeDescriptor sourceType, @Nonnull TypeDescriptor typeDescriptor) {
      String string = JsonUtils.JSON.toJson(source);
      return string;
    }
  }

  @ReadingConverter
  @RequiredArgsConstructor
  public static class EntityReadConverter implements GenericConverter {
    private final Set<ConvertiblePair> convertiblePairs;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return convertiblePairs;
    }

    @Override
    public Object convert(
        Object source, @Nonnull TypeDescriptor sourceType, @Nonnull TypeDescriptor targetType) {
      JSONObject jsonObject = JsonUtils.JSON.convert(String.valueOf(source), JSONObject.class);
      return JsonUtils.JSON.fromJson(jsonObject.toString(), targetType.getObjectType());
    }
  }

  @WritingConverter
  @RequiredArgsConstructor
  public static class EnumWriteConverter implements GenericConverter {
    private final Set<ConvertiblePair> convertiblePairs;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return convertiblePairs;
    }

    @Override
    @SneakyThrows
    public String convert(
        Object source, @Nonnull TypeDescriptor sourceType, @Nonnull TypeDescriptor targetType) {
      return String.valueOf(source);
    }
  }

  @ReadingConverter
  @RequiredArgsConstructor
  public static class EnumReadConverter implements GenericConverter {
    private final Set<ConvertiblePair> convertiblePairs;

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
      return convertiblePairs;
    }

    @Override
    @SneakyThrows
    public Object convert(
        Object source, @Nonnull TypeDescriptor sourceType, @Nonnull TypeDescriptor targetType) {
      Method valueOf = targetType.getType().getMethod("valueOf", String.class);
      return valueOf.invoke(null, String.valueOf(source).replace("%", "").replace("\"", ""));
    }
  }

  @WritingConverter
  @RequiredArgsConstructor
  public static class CommonSetWriter implements Converter<Set<CommonObject>, JSONObject> {

    @Override
    public JSONObject convert(@NotNull Set<CommonObject> source) {
      if (source.stream().filter(Objects::nonNull).collect(Collectors.toSet()).isEmpty()) {
        return null;
      }
      JSONObject object = ConversionUtils.writeSet(source);
      return object;
    }
  }

  @ReadingConverter
  @RequiredArgsConstructor
  public static class CommonSetReader implements Converter<String, Set<? extends CommonObject>> {

    @Override
    public Set<? extends CommonObject> convert(@NotNull String source) {
      JSONObject jsonObject = JsonUtils.JSON.convert(source, JSONObject.class);
      Set<CommonObject> objects = ConversionUtils.readJson(jsonObject);
      return objects;
    }
  }

  @WritingConverter
  @RequiredArgsConstructor
  public static class LocalTimeWriter implements Converter<OffsetDateTime, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NotNull OffsetDateTime source) {
      return source.toLocalDateTime();
    }
  }

  @ReadingConverter
  @RequiredArgsConstructor
  public static class LocalTimeReader implements Converter<LocalDateTime, OffsetDateTime> {

    @Override
    public OffsetDateTime convert(@NotNull LocalDateTime source) {
      return OffsetDateTime.of(source, UTC);
    }
  }
}
