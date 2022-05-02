package com.firmys.gameservices.characters.service;

import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServiceConstants.CHARACTER + ServiceConstants.CACHE_MANAGER_SUFFIX)
public class CharacterCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceConstants.CHARACTER;

    public CharacterCacheManager() {
        super(cacheName);
    }
}
