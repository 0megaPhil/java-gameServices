package com.firmys.gameservices.service.utils;

import static com.firmys.gameservices.common.FunctionUtils.orThrow;
import static com.firmys.gameservices.common.FunctionUtils.orVoid;
import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.fasterxml.jackson.core.type.TypeReference;
import com.firmys.gameservices.generated.models.CommonObject;
import com.firmys.gameservices.generated.models.Error;
import com.firmys.gameservices.service.error.ServiceException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

@SuppressWarnings("unchecked")
public class ConversionUtils {
  private static String sourceString(Object typeSetSource) {
    return Optional.ofNullable(typeSetSource)
        .filter(src -> String.class.isAssignableFrom(src.getClass()))
        .map(src -> (String) src)
        .orElseThrow(
            () ->
                new ServiceException(
                    Error.builder()
                        .title("CANNOT PARSE")
                        .message("unable to parse " + typeSetSource)
                        .data(orVoid(() -> String.valueOf(typeSetSource)))
                        .build()));
  }

  public static <E extends CommonObject> Set<E> readSet(Object typeSetSource) {
    String typeSetString = sourceString(typeSetSource);
    TypeReference<Map.Entry<String, Object[]>> reference = new TypeReference<>() {};
    Map.Entry<String, Object[]> typeSetPair = JSON.fromJson(typeSetString, reference);
    Class<E> objectType = (Class<E>) orThrow(() -> Class.forName(typeSetPair.getKey()));
    return Arrays.stream(typeSetPair.getValue())
        .parallel()
        .map(obj -> JSON.convert(obj, objectType))
        .collect(Collectors.toSet());
  }

  public static <E extends CommonObject> Class<E> commonType(Collection<E> objects) {
    return Optional.of(objects)
        .filter(objs -> !objs.isEmpty())
        .map(objs -> Set.copyOf(objs).iterator().next())
        .map(obj -> (Class<E>) obj.getClass())
        .orElseGet(() -> (Class<E>) CommonObject.class);
  }

  public static <E extends CommonObject> JSONObject writeSet(Collection<E> objects) {
    JSONArray jsonArray = JSON.convert(objects, JSONArray.class);
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("objectType", commonType(objects).getTypeName());
    jsonObject.put("elements", jsonArray);
    return jsonObject;
  }

  @SneakyThrows
  public static <E extends CommonObject> Set<E> readJson(JSONObject jsonObject) {
    Set<E> objects = new HashSet<>();
    if (jsonObject.has("objectType")) {
      Class<E> objectType = (Class<E>) Class.forName(jsonObject.getString("objectType"));
      JSONArray jsonArray = jsonObject.getJSONArray("elements");

      jsonArray.forEach(json -> objects.add(JSON.convert(json, objectType)));
    }
    return objects;
  }
}
