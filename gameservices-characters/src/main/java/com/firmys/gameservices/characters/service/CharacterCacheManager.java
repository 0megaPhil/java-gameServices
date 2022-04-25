package com.firmys.gameservices.characters.service;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceStrings.CHARACTER + ServiceStrings.CACHE_MANAGER_SUFFIX)
public class CharacterCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceStrings.CHARACTER;

    public CharacterCacheManager() {
        super(cacheName);
    }
}
