package com.firmys.gameservices.common.data;

import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.firmys.gameservices.common.CommonObject;
import jakarta.annotation.Nonnull;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.TypeDescriptor;
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
      return ((CommonObject) source).toJson();
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
        Object source, @Nonnull TypeDescriptor sourceType, @Nonnull TypeDescriptor typeDescriptor) {
      return JSON.fromJson((String) source, typeDescriptor.getObjectType());
    }
  }
}
