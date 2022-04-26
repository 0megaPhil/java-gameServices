package com.firmys.gameservices.common;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameDataLookup<T extends GameEntity> {
    private final Map<UUID, Integer> uuidPrimaryKeyMap = new ConcurrentHashMap<>();
    private final GameService<T> service;

    public GameDataLookup(GameService<T> service) {
        this.service = service;
    }

    public Integer getPrimaryKeyByUuid(UUID uuid) {
        return getPrimaryKeyByUuid(uuid.toString());
    }

    public Integer getPrimaryKeyByUuid(String uuidString) {
        if(!uuidPrimaryKeyMap.containsKey(UUID.fromString(uuidString))) {
            service.findAll().forEach(d -> uuidPrimaryKeyMap.computeIfAbsent(d.getUuid(), v -> d.getId()));
        }
        return Optional.ofNullable(uuidPrimaryKeyMap.get(UUID.fromString(uuidString)))
                .orElseThrow(() -> new RuntimeException("UUID not found in GameDataLookup for " +
                        service.getClass().getSimpleName() + " use a valid existing UUID"));
    }

    private int computeUuidIds(String uuidString) {
        if(!uuidPrimaryKeyMap.containsKey(UUID.fromString(uuidString))) {
            service.findAll().forEach(d -> uuidPrimaryKeyMap.putIfAbsent(d.getUuid(), d.getId()));
        }
        return Optional.ofNullable(uuidPrimaryKeyMap.get(UUID.fromString(uuidString)))
                .orElseThrow(() -> new RuntimeException("UUID not found in GameDataLookup for " +
                        service.getClass().getSimpleName() + " use a valid existing UUID"));
    }

}
