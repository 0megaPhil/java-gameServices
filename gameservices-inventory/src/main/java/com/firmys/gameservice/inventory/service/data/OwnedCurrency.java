package com.firmys.gameservice.inventory.service.data;

import com.firmys.gameservice.common.GameServiceError;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.inventory.service.controllers.CurrencyController;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OwnedCurrency implements Serializable {

    private UUID uuid = UUID.randomUUID();
    private final Map<UUID, Integer> currencyOwnedMap = new ConcurrentHashMap<>();

    public OwnedCurrency() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Map<UUID, Integer> getCurrencyOwnedMap() {
        return currencyOwnedMap;
    }

    public Integer getCurrencyAmount(Currency currency) {
        return getCurrencyAmount(currency.getUuid().toString());
    }

    public Integer getCurrencyAmount(UUID uuid) {
        return getCurrencyAmount(uuid.toString());
    }

    public Integer getCurrencyAmount(String uuidString) {
        return currencyOwnedMap.putIfAbsent(UUID.fromString(uuidString), 0);
    }

    public Integer creditCurrency(Currency currency, Integer amount) {
        return creditCurrency(currency.getUuid().toString(), amount);
    }

    public Integer debitCurrency(Currency currency, Integer amount) {
        return debitCurrency(currency.getUuid().toString(), amount);
    }

    public Integer creditCurrency(UUID uuid, Integer amount) {
        return creditCurrency(uuid.toString(), amount);
    }

    public Integer debitCurrency(UUID uuid, Integer amount) {
        return debitCurrency(uuid.toString(), amount);
    }

    public Integer creditCurrency(String currencyUuidString, Integer amount) {
        getCurrencyAmount(currencyUuidString);
        return currencyOwnedMap.computeIfPresent(UUID.fromString(currencyUuidString), (c, a) -> a + amount);
    }

    public Integer debitCurrency(String uuidString, Integer amount) {
       if(getCurrencyAmount(uuidString) < amount) {
           throw new RuntimeException((new GameServiceError(new Currency(),
                   ServiceConstants.CURRENCY,
                   getCurrencyAmount(uuidString) + " not enough to cover transaction " + amount, null)).toString());
       }
       return currencyOwnedMap.computeIfPresent(UUID.fromString(uuidString), (c, a) -> a - amount);
    }
}
