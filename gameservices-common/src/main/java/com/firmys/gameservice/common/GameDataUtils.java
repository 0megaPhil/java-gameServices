package com.firmys.gameservice.common;

import com.fasterxml.jackson.databind.JavaType;

import java.lang.reflect.Field;
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
        if(original instanceof GameEntity) {
            originalMap.putIfAbsent("id", ((GameEntity) original).getId());
            copyableMap.putIfAbsent("id", ((GameEntity) original).getId());
        }
        /*
         * Only in the case of
         */
        copyableMap.forEach((k, v) -> {
            if (v != null && !(k.equals("id") || k.equals("uuid"))) {
                originalMap.computeIfPresent(k, (originalKey, originalValue) -> v);
                originalMap.putIfAbsent(k, v);
            }
        });
        D adjusted = JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(originalMap),
                JsonUtils.getMapper().getTypeFactory().constructType(objectClass));
        if(original instanceof GameEntity) {
            setFieldValue(adjusted, "id", ((GameEntity) original).getId());
        }
        return adjusted;
    }

    public static Field setFieldValue(Object object, String fieldName, Object valueTobeSet) {
        Field field = null;
        try {
            field = getField(object.getClass(), fieldName);
            field.setAccessible(true);
            field.set(object, valueTobeSet);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return field;
    }

    public static Object getPrivateFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = getField(object.getClass(), fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private static Field getField(Class<?> mClass, String fieldName) throws NoSuchFieldException {
        try {
            return mClass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = mClass.getSuperclass();
            if (superClass == null) {
                throw e;
            } else {
                return getField(superClass, fieldName);
            }
        }
    }
}
