package com.firmys.gameservice.common;

import java.util.Map;
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
            service.findAll().forEach(d -> uuidPrimaryKeyMap.putIfAbsent(d.getUuid(), d.getId()));
        }
        return uuidPrimaryKeyMap.get(UUID.fromString(uuidString));
    }

    private int computeUuidIds(String uuidString) {
        if(!uuidPrimaryKeyMap.containsKey(UUID.fromString(uuidString))) {
            service.findAll().forEach(d -> uuidPrimaryKeyMap.putIfAbsent(d.getUuid(), d.getId()));
        }
        return uuidPrimaryKeyMap.get(UUID.fromString(uuidString));
    }

}
