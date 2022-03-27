package com.firmys.gameservice.common;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameDataLookup<T extends GameData> {
    private final Map<UUID, Integer> uuidPrimaryKeyMap = new ConcurrentHashMap<>();
    private final Map<UUID, T> uuidObjectMap = new ConcurrentHashMap<>();
    private final GameService<T> service;


    public GameDataLookup(GameService<T> service) {
        this.service = service;
    }

    public Integer getPrimaryKeyByUuid(UUID uuid) {
        if (!uuidPrimaryKeyMap.containsKey(uuid)) {
            return getPrimaryKeyByUuid(uuid.toString());
        }
        return uuidPrimaryKeyMap.get(uuid);
    }

    public Integer getPrimaryKeyByUuid(String uuidString) {
        checkAndRefresh(uuidString);
        UUID key = uuidPrimaryKeyMap.keySet().stream()
                .filter(u -> u.toString().equals(uuidString)).findFirst()
                .orElseThrow(() -> new RuntimeException("No Primary Key found for UUID - " + uuidString));
        return uuidPrimaryKeyMap.get(key);
    }

    public T getDataByUuid(UUID uuid) {
        if (!uuidObjectMap.containsKey(uuid)) {
            return getDataByUuid(uuid.toString());
        }
        return uuidObjectMap.get(uuid);
    }

    public T getDataByUuid(String uuidString) {
        checkAndRefresh(uuidString);
        UUID key = uuidObjectMap.keySet().stream()
                .filter(u -> u.toString().equals(uuidString)).findFirst()
                .orElseThrow(() -> new RuntimeException("No Data for UUID - " + uuidString));
        return uuidObjectMap.get(key);
    }

    private void checkAndRefresh(String uuidString) {
        if (uuidPrimaryKeyMap.keySet().stream()
                .noneMatch(u -> u.toString().equals(uuidString)) ||
                uuidObjectMap.keySet().stream()
                        .noneMatch(u -> u.toString().equals(uuidString))) {
            service.findAll().forEach(d -> {
                uuidPrimaryKeyMap.putIfAbsent(UUID.fromString(uuidString), d.getId());
                uuidObjectMap.putIfAbsent(UUID.fromString(uuidString), d);
            });
        }
    }

}
