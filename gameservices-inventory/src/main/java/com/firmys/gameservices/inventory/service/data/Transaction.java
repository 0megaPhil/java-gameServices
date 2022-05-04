package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.Transactions;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
public class Transaction extends AbstractGameEntity implements Comparable<Transaction> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid;
    private UUID currencyUuid;
    @ManyToOne
    @JsonIgnore
    private TransactionalCurrency transactionalCurrency;
    private String transactionType;
    private long start;
    private long amount;
    private long result;
    private Long epochMillis;
    private String dateTime;
    private String date;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public Transaction() {}

    public Transaction(TransactionalCurrency transactionalCurrency,
                Transactions transactionType, UUID currencyUuid, long amount, long start, long result) {
        this.transactionalCurrency = transactionalCurrency;
        this.transactionType = transactionType.name();
        this.currencyUuid = currencyUuid;
        this.start = start;
        this.amount = amount;
        this.result = result;
        LocalDateTime localDateTime = LocalDateTime.now();
        this.epochMillis = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();

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

    public long getResult() {
        return result;
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

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int compareTo(Transaction transaction) {
        return this.epochMillis.compareTo(transaction.epochMillis);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "uuid=" + uuid +
                ", currencyUuid=" + currencyUuid +
                ", transactionType=" + transactionType +
                ", start=" + start +
                ", amount=" + amount +
                ", end=" + result +
                ", epochMillis=" + epochMillis +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    public String getTransactionType() {
        return transactionType;
    }

    public TransactionalCurrency getTransactionalCurrency() {
        return transactionalCurrency;
    }
}
