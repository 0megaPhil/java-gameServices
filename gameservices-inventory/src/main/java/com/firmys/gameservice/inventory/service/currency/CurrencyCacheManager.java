package com.firmys.gameservice.inventory.service.currency;

import com.firmys.gameservice.common.GameDataCacheManager;
import com.firmys.gameservice.common.ServicePaths;
import org.springframework.stereotype.Component;

@Component(ServicePaths.CURRENCY + ServicePaths.CACHE_MANAGER_SUFFIX)
public class CurrencyCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServicePaths.CURRENCY;

    public CurrencyCacheManager() {
        super(cacheName);
    }
}
