package com.firmys.gameservices.common;

import java.util.UUID;
import java.util.function.Function;

public class ErrorMessages {

    public static Function<Class<? extends GameData>, String> notFoundByUuid(UUID uuid) {
        return d -> d.getSimpleName() + " not found by UUID -> " + uuid.toString() + " <-";
    }

    public static Function<Class<? extends GameData>, String> notFoundByUuid(String uuid) {
        return d -> notFoundByUuid(UUID.fromString(uuid)).apply(d);
    }

    public static Function<Class<? extends GameData>, RuntimeException> notFoundByUuidException(UUID uuid) {
        return d -> new RuntimeException(notFoundByUuid(uuid).apply(d));
    }

    public static Function<Class<? extends GameData>, RuntimeException> notFoundByUuidException(String uuid) {
        return d -> new RuntimeException(notFoundByUuid(uuid).apply(d));
    }
}

