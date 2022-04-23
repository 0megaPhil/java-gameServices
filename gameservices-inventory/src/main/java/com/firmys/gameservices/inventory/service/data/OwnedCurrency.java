package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.GameData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
 * FIXME - Rolling integer, causing record deletion
 * FIXME - TRANSACTION UUIDs
 * FIXME - Currency UUID not showing up in OwnedCurrency
 */
public class OwnedCurrency implements GameData {
    private UUID currencyUuid;
    private AtomicInteger totalCurrency = new AtomicInteger(0);
    private final AtomicReference<SortedSet<UUID>> transactionUuids = new AtomicReference<>(new TreeSet<>());

    public OwnedCurrency(Currency currency) {
        this.currencyUuid = currency.getUuid();
    }

    public int getCount() {
        return totalCurrency.get();
    }

    public OwnedCurrency credit() {
        return credit(1);
    }

    public OwnedCurrency credit(int amount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        LocalDateTime localDateTime = LocalDateTime.now();
        totalCurrency.getAndUpdate(a -> a + amount);
        transactionUuids.getAndUpdate(u -> {
            u.add(UUID.nameUUIDFromBytes(("CREDIT: " + amount + " - " + dtf.format(localDateTime)).getBytes()));
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
        transactionUuids.getAndUpdate(u -> {
            u.add(UUID.nameUUIDFromBytes(("DEBIT: " + amount + " - " + dtf.format(localDate)).getBytes()));
            return u;
        });
        return this;
    }

    public void setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
    }

    public Set<UUID> getTransactionUuids() {
        return transactionUuids.get();
    }

}
