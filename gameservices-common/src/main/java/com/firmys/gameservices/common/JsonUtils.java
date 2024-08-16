package com.firmys.gameservices.common;

import static com.firmys.gameservices.common.FunctionUtils.orVoid;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firmys.gameservices.generated.models.Options;
import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

@Service
@RequiredArgsConstructor
public class JsonUtils {

  public static JsonUtils JSON = new JsonUtils(new ObjectMapper());

  private final ObjectMapper mapper;

  @SneakyThrows
  public <T> String toJson(T object) {
    return mapper.writeValueAsString(object);
  }

  @SneakyThrows
  public <T> LinkedMultiValueMap<String, String> toMap(T object) {
    Map<String, Object> map = mapper.convertValue(object, new TypeReference<>() {});
    // Map to MultiValueMap
    LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
    map.entrySet().stream()
        .filter(entry -> String.class.isAssignableFrom(entry.getValue().getClass()))
        .forEach(en -> linkedMultiValueMap.add(en.getKey(), String.valueOf(en.getValue())));
    return linkedMultiValueMap;
  }

  @SneakyThrows
  public <T> T fromJson(String jsonStr, Class<T> objectType) {
    jsonStr = StringEscapeUtils.unescapeJson(jsonStr.replace("\"{", "{").replace("}\"", "}"));
    return mapper.readValue(jsonStr, objectType);
  }

  @SneakyThrows
  public <T> T convert(Object jsonStr, Class<T> objectType) {
    return mapper.convertValue(jsonStr, objectType);
  }

  @SneakyThrows
  public <T> T convert(Object jsonStr, TypeReference<T> objectType) {
    return mapper.convertValue(jsonStr, objectType);
  }

  @SneakyThrows
  public <T> T fromJson(String jsonStr, TypeReference<T> objectType) {
    return mapper.readValue(jsonStr, objectType);
  }

  public Options options(@Nullable Map<String, String> queryParams) {
    queryParams = Optional.ofNullable(queryParams).orElse(Map.of());
    return Options.builder()
        .sortBy(Optional.ofNullable(queryParams.get("sort")).orElse("id"))
        .limit(
            Optional.ofNullable(queryParams.get("limit"))
                .map(lim -> orVoid(() -> Integer.valueOf(lim)))
                .orElse(1000))
        .build();
  }

  @PostConstruct
  public void postConstruct() {
    JSON = this;
  }
}
