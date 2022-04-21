package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.GameData;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class OwnedCurrency implements GameData {
    private UUID currencyUuid;
    private final AtomicReference<SortedSet<UUID>> UUIDs = new AtomicReference<>(new TreeSet<>());
    private int count = UUIDs.get().size();

    public OwnedCurrency(Currency currency) {
        this.currencyUuid = currency.getUuid();
    }

    public int getCount() {
        return count;
    }

    public OwnedCurrency credit() {
        return credit(1);
    }

    public OwnedCurrency credit(int amount) {
        UUIDs.getAndUpdate(u -> {
            IntStream.range(0, amount)
                    .forEach(i -> u.add(UUID.randomUUID()));
            return u;
        });
        return this;
    }

    public OwnedCurrency debit() {
        return debit(1);
    }

    public OwnedCurrency debit(int amount) {
        if(getCount() < amount) {
            throw new RuntimeException("Insufficient currency count of " +
                    getCount() + " for consumption of " + amount + " Currency " + currencyUuid.toString());
        }
        UUIDs.getAndUpdate(u -> {
            IntStream.range(0, amount)
                    .forEach(i -> u.remove(UUIDs.get().last()));
            return u;
        });
        return this;
    }

    public void setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
    }

    public Set<UUID> getUUIDs() {
        return UUIDs.get();
    }

}
