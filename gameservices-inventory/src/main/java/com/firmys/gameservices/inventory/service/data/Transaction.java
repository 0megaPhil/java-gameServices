package com.firmys.gameservices.inventory.service.data;

import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.data.Transactions;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class Transaction implements GameData, Comparable<Long> {

    private final UUID uuid = UUID.randomUUID();
    private final UUID currencyUuid;
    private final Transactions transactionType;
    private final long start;
    private final long amount;
    private final long end;
    private final LocalDateTime localDateTime;
    private final Long epochMillis;
    private final String dateTime;
    private final String date;

    Transaction(Transactions transactionType, UUID currencyUuid, long amount, long start, long end) {
        this.transactionType = transactionType;
        this.currencyUuid = currencyUuid;
        this.start = start;
        this.amount = amount;
        this.end = end;
        this.localDateTime = LocalDateTime.now();
        this.epochMillis = this.localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

        this.dateTime = Formatters.dateTimeFormatter.format(localDateTime);
        this.date = Formatters.dateFormatter.format(localDateTime);
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public long getStart() {
        return start;
    }

    public long getAmount() {
        return amount;
    }

    public long getEnd() {
        return end;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getDate() {
        return date;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public int compareTo(Long epochMillis) {
        return this.epochMillis.compareTo(epochMillis);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "uuid=" + uuid +
                ", currencyUuid=" + currencyUuid +
                ", transactionType=" + transactionType +
                ", start=" + start +
                ", amount=" + amount +
                ", end=" + end +
                ", epochMillis=" + epochMillis +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public Transactions getTransactionType() {
        return transactionType;
    }
}