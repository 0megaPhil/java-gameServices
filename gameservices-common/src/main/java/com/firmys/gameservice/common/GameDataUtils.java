package com.firmys.gameservice.common;

import com.fasterxml.jackson.databind.JavaType;

import java.util.Map;

public class GameDataUtils {
    /**
     * Update objects by deserializing, converting to map and copying values if available
     *
     * @param objectClass class of object for {@link JavaType} generation
     * @param original    original {@link GameData} object to have new data applied
     * @param copyable    new entity to be copied from
     * @param <D>         type for updated entity
     * @return original entity with new data from copyable applied
     */
    public static <D extends GameData> D update(Class<D> objectClass, D original, D copyable) {
        Map<String, Object> originalMap = JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(original),
                JsonUtils.getMapper().getTypeFactory().constructType(Map.class));
        Map<String, Object> copyableMap = JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(copyable),
                JsonUtils.getMapper().getTypeFactory().constructType(Map.class));
        copyableMap.forEach((k, v) -> {
            if (v != null && !(k.equals("id") || k.equals("uuid"))) {
                originalMap.computeIfPresent(k, (originalKey, originalValue) -> v);
                originalMap.putIfAbsent(k, v);
            }
        });
        return JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(originalMap),
                JsonUtils.getMapper().getTypeFactory().constructType(objectClass));
    }
}
