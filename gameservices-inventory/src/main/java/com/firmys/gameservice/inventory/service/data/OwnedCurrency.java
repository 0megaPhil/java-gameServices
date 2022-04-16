package com.firmys.gameservice.inventory.service.data;

import com.firmys.gameservice.common.GameData;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OwnedCurrency implements GameData {

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
           throw new RuntimeException("For " + uuidString + " available value of " +
                   getCurrencyAmount(uuidString) + " lower than requested debit amount of " + amount);
       }
       return currencyOwnedMap.computeIfPresent(UUID.fromString(uuidString), (c, a) -> a - amount);
    }

}
