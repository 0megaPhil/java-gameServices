package com.firmys.gameservices.common;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

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
  public <T> T fromJson(String jsonStr, Class<T> objectType) {
    return mapper.readValue(jsonStr, objectType);
  }

  @SneakyThrows
  public <T> T fromJson(String jsonStr, TypeReference<T> objectType) {
    return mapper.readValue(jsonStr, objectType);
  }

  @PostConstruct
  public void postConstruct() {
    JSON = this;
  }
}
