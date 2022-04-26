package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.AbstractGameData;
import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.data.Transactions;

import java.time.LocalDate;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/*
 * FIXME - Rolling integer, causing record deletion
 * FIXME - TRANSACTION UUIDs
 * FIXME - Currency UUID not showing up in OwnedCurrency
 * TODO - Add rollover and backup of old transactions
 */
public class OwnedCurrency extends AbstractGameData {

    private UUID currencyUuid;
    private AtomicLong totalCurrency = new AtomicLong(0);
    private final AtomicReference<SortedSet<Transaction>> transactions = new AtomicReference<>(new TreeSet<>());

    public OwnedCurrency(Currency currency) {
        this.currencyUuid = currency.getUuid();
    }

    public long getCount() {
        return totalCurrency.get();
    }

    public OwnedCurrency credit() {
        return credit(1);
    }

    public OwnedCurrency credit(long amount) {
        this.transactions.getAndUpdate(tSet -> {
            if(totalCurrency.get() + amount >= Long.MAX_VALUE) {
                throw new RuntimeException("Currency count " +
                        getCount() + " plus " + amount + " for Currency " + currencyUuid.toString() +
                        " would exceed cap of " + Long.MAX_VALUE);
            }

            long start = this.totalCurrency.getAndUpdate(a -> a + amount);
            long end = this.totalCurrency.get();
            tSet.add(new Transaction(Transactions.CREDIT, this.currencyUuid, amount, start, end));
            return tSet;
        });
        return this;
    }

    public OwnedCurrency debit() {
        return debit(1);
    }

    public OwnedCurrency debit(long amount) {
        this.transactions.getAndUpdate(tSet -> {
            if(totalCurrency.get() < amount) {
                throw new RuntimeException("Insufficient currency count of " +
                        getCount() + " for consumption of " + amount + " Currency " + currencyUuid.toString());
            }
            long start = this.totalCurrency.getAndUpdate(a -> a - amount);
            long end = this.totalCurrency.get();
            tSet.add(new Transaction(Transactions.DEBIT, this.currencyUuid, amount, start, end));
            return tSet;
        });
        return this;
    }

    public Set<Transaction> getTransactions() {
        return transactions.get();
    }

    public Set<Transaction> getTransactionsByDate(LocalDate localDate) {
        return this.transactions.get().stream()
                .filter(t -> t.getDate()
                        .contains(Formatters.dateFormatter.format(localDate))).collect(Collectors.toSet());
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }
}
