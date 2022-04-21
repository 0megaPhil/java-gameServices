package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.GameData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class OwnedCurrency implements GameData {
    private UUID currencyUuid;
    private AtomicInteger totalCurrency = new AtomicInteger(0);
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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        totalCurrency.getAndUpdate(a -> a + amount);
        UUIDs.getAndUpdate(u -> {
            u.add(UUID.fromString("CREDIT: " + amount + " - " + dtf.format(localDate)));
            return u;
        });
        return this;
    }

    public OwnedCurrency debit() {
        return debit(1);
    }

    public OwnedCurrency debit(int amount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDate localDate = LocalDate.now();
        if(totalCurrency.get() < amount) {
            throw new RuntimeException("Insufficient currency count of " +
                    getCount() + " for consumption of " + amount + " Currency " + currencyUuid.toString());
        }
        totalCurrency.getAndUpdate(a -> a - amount);
        UUIDs.getAndUpdate(u -> {
            u.add(UUID.fromString("DEBIT: " + amount + " - " + dtf.format(localDate)));
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
