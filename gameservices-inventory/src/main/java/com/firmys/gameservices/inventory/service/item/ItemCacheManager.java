package com.firmys.gameservices.inventory.service.item;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServicePaths.ITEM + ServicePaths.CACHE_MANAGER_SUFFIX)
public class ItemCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServicePaths.ITEM;

    public ItemCacheManager() {
        super(cacheName);
    }
}
