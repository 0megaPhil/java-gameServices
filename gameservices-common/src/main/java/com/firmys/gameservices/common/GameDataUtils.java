package com.firmys.gameservices.common;

import com.fasterxml.jackson.databind.JavaType;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
        AtomicReference<Map<String, Object>> originalMap = new AtomicReference<>(JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(original),
                JsonUtils.getMapper().getTypeFactory().constructType(Map.class)));
        AtomicReference<Map<String, Object>> copyableMap = new AtomicReference<>(JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(copyable),
                JsonUtils.getMapper().getTypeFactory().constructType(Map.class)));
        if(original instanceof GameEntity) {
            originalMap.get().putIfAbsent("id", ((GameEntity) original).getId());
            copyableMap.get().putIfAbsent("id", ((GameEntity) original).getId());
        }
        copyableMap.get().forEach((k, v) -> {
            if(!((v instanceof Integer) && Integer.parseInt(v.toString()) == -1)) {
                if ((v != null && !(k.equals("id") || k.equals("uuid")))) {
                    originalMap.get().computeIfPresent(k, (originalKey, originalValue) -> v);
                    originalMap.get().putIfAbsent(k, v);
                }
            }
        });
        D adjusted = JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(originalMap),
                objectClass);
        if(original instanceof GameEntity) {
            setFieldValue(adjusted, "id", ((GameEntity) original).getId());
        }
        return adjusted;
    }

    public static <D extends GameData> Set<String> getEntityAttributes(D entity) {
        AtomicReference<Map<String, Object>> originalMap = new AtomicReference<>(JsonUtils.mapJsonToObject(
                JsonUtils.writeObjectAsString(entity),
                JsonUtils.getMapper().getTypeFactory().constructType(Map.class)));
        return originalMap.get().keySet();
    }

    public static <D extends GameData> Set<D> matchByAnyAttributes(Map<String, String> attributes,
                                                                   Set<D> entities,
                                                                   boolean partial) {
        if(partial) {
            return getEntityAttributeMap(entities).entrySet().stream().filter(
                            entry -> entry.getValue().entrySet().stream()
                                    .filter(e -> attributes.containsKey(e.getKey().toLowerCase()))
                                    .anyMatch(e -> e.getValue().toLowerCase().contains(attributes.get(e.getKey()))))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
        }
        return getEntityAttributeMap(entities).entrySet().stream().filter(
                        entry -> entry.getValue().entrySet().stream()
                                .filter(e -> attributes.containsKey(e.getKey().toLowerCase()))
                                .anyMatch(e -> attributes.get(e.getKey()).equalsIgnoreCase(e.getValue())))
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static <D extends GameData> Set<D> matchByAllAttributes(Map<String, String> attributes,
                                                                   Set<D> entities,
                                                                   boolean partial) {
        if(partial) {
            return getEntityAttributeMap(entities).entrySet().stream().filter(
                            entry -> entry.getValue().entrySet().stream()
                                    .filter(e -> attributes.containsKey(e.getKey().toLowerCase()))
                                    .allMatch(e -> e.getValue().toLowerCase().contains(attributes.get(e.getKey()))))
                    .map(Map.Entry::getKey).collect(Collectors.toSet());
        }
        return getEntityAttributeMap(entities).entrySet().stream().filter(
                entry -> entry.getValue().entrySet().stream()
                        .filter(e -> attributes.containsKey(e.getKey().toLowerCase()))
                        .allMatch(e -> attributes.get(e.getKey()).equalsIgnoreCase(e.getValue())))
                .map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    public static <D extends GameData> Map<D, Map<String, String>> getEntityAttributeMap(Set<D> entities) {
        return entities.stream().map(e -> Map.of(e, JsonUtils.mapJsonToMap(
                        JsonUtils.writeObjectAsString(e),
                        JsonUtils.getMapper().getTypeFactory().constructMapType(Map.class, String.class, String.class),
                        String.class, String.class)))
                .flatMap(m -> m.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
