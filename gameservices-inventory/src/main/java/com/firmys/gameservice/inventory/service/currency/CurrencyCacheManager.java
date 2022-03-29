package com.firmys.gameservice.inventory.service.currency;

import com.firmys.gameservice.common.GameDataCacheManager;
import com.firmys.gameservice.common.ServiceConstants;
import org.springframework.stereotype.Component;

@Component(ServiceConstants.CURRENCY + ServiceConstants.CACHE_MANAGER_SUFFIX)
public class CurrencyCacheManager extends GameDataCacheManager {
    private final static String cacheName = ServiceConstants.CURRENCY;

    public CurrencyCacheManager() {
        super(cacheName);
    }
}
