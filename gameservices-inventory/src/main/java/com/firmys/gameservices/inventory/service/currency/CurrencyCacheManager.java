package com.firmys.gameservices.inventory.service.currency;

import com.firmys.gameservices.common.GameDataCacheManager;
import com.firmys.gameservices.common.ServicePaths;
import org.springframework.stereotype.Component;

@Component(ServicePaths.CURRENCY + ServicePaths.CACHE_MANAGER_SUFFIX)
public class CurrencyCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServicePaths.CURRENCY;

    public CurrencyCacheManager() {
        super(cacheName);
    }
}
