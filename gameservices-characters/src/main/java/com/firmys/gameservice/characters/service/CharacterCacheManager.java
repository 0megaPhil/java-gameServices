package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.common.ServicePaths;
import com.firmys.gameservice.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServicePaths.CHARACTER + ServicePaths.CACHE_MANAGER_SUFFIX)
public class CharacterCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServicePaths.CHARACTER;

    public CharacterCacheManager() {
        super(cacheName);
    }
}
