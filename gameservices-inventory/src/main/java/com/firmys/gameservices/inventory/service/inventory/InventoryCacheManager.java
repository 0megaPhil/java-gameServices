package com.firmys.gameservices.inventory.service.inventory;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.common.GameDataCacheManager;
import org.springframework.stereotype.Component;

@Component(ServicePaths.INVENTORY + ServicePaths.CACHE_MANAGER_SUFFIX)
public class InventoryCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServicePaths.INVENTORY;

    public InventoryCacheManager() {
        super(cacheName);
    }
}
