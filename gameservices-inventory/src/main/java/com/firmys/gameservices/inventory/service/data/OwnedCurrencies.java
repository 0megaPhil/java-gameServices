package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.GameData;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OwnedCurrencies implements GameData {

    private UUID uuid = UUID.randomUUID();
    private Map<UUID, OwnedCurrency> ownedCurrencyMap = new ConcurrentHashMap<>();

    public OwnedCurrencies() {
    }

    public void setOwnedCurrencyMap(Map<UUID, OwnedCurrency> ownedCurrencyMap) {
        this.ownedCurrencyMap = ownedCurrencyMap;
    }

    public Map<UUID, OwnedCurrency> getOwnedCurrencyMap() {
        return ownedCurrencyMap;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public OwnedCurrencies creditCurrency(Currency currency, int amount) {
        OwnedCurrency ownedCurrency = ownedCurrencyMap
                .computeIfAbsent(currency.getUuid(), v -> new OwnedCurrency(currency));
        ownedCurrency.credit(amount);
        return this;
    }

    public OwnedCurrencies debitCurrency(Currency currency, int amount) {
        OwnedCurrency ownedCurrency = ownedCurrencyMap
                .computeIfAbsent(currency.getUuid(), v -> new OwnedCurrency(currency));
        ownedCurrency.debit(amount);
        return this;
    }

    public OwnedCurrency getOwnedCurrencyByCurrency(Currency currency) {
        return Optional.ofNullable(this.getOwnedCurrencyMap()
                .get(currency.getUuid())).orElse(new OwnedCurrency(currency));
    }

}
