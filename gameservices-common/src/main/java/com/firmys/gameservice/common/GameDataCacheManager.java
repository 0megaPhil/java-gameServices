package com.firmys.gameservice.common;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

public class GameDataCacheManager extends ConcurrentMapCacheManager {
    private final String cacheName;

    public GameDataCacheManager(String cacheName) {
        this.cacheName = cacheName;
    }

    public Cache getCache() {
        return super.getCache(cacheName);
    }
}
