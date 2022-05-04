package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.Formatters;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.Transactions;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
 * FIXME - Rolling integer, causing record deletion
 * FIXME - TRANSACTION UUIDs
 * FIXME - Currency UUID not showing up in OwnedCurrency
 * TODO - Add rollover and backup of old transactions
 */
@Entity
public class TransactionalCurrency extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();
    private UUID currencyUuid;
    private Long totalCurrency;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = ServiceConstants.INVENTORY + ServiceConstants.ID)
    private Inventory inventory;
    @Column(length = 1000000)
    @ElementCollection(targetClass = Transaction.class)
    @OneToMany(mappedBy = ServiceConstants.TRANSACTIONAL_CURRENCY, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Transaction> transactions = ConcurrentHashMap.newKeySet();

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        transactions = ConcurrentHashMap.newKeySet();
        totalCurrency = 0L;
    }

    public TransactionalCurrency() {}

    public TransactionalCurrency(Inventory inventory, Currency currency) {
        this.currencyUuid = currency.getUuid();
        this.inventory = inventory;
        this.totalCurrency = 0L;
    }

    public void setCurrencyUuid(UUID currencyUuid) {
        this.currencyUuid = currencyUuid;
    }

    public void setTotalCurrency(Long totalCurrency) {
        this.totalCurrency = totalCurrency;
    }

    public long getTotalCurrency() {
        return totalCurrency;
    }

    public TransactionalCurrency credit() {
        return credit(1);
    }

    public TransactionalCurrency credit(long amount) {
        if(totalCurrency + amount >= Long.MAX_VALUE) {
            throw new RuntimeException("Currency count " +
                    getTotalCurrency() + " plus " + amount + " for Currency " + currencyUuid.toString() +
                    " would exceed cap of " + Long.MAX_VALUE);
        }
        Transaction transaction = new Transaction(this, Transactions.CREDIT,
                this.currencyUuid, amount, totalCurrency, (totalCurrency + amount));
        transactions.add(transaction);
        totalCurrency = transaction.getResult();
        return this;
    }

    public TransactionalCurrency debit() {
        return debit(1);
    }

    public TransactionalCurrency debit(long amount) {
        if(totalCurrency - amount < 0) {
            throw new RuntimeException("Insufficient currency count of " +
                    getTotalCurrency() + " for consumption of " + amount + " Currency " + currencyUuid.toString());
        }

        Transaction transaction = new Transaction(this, Transactions.DEBIT,
                this.currencyUuid, amount, totalCurrency, (totalCurrency - amount));
        transactions.add(transaction);
        totalCurrency = transaction.getResult();
        return this;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public Set<Transaction> getTransactionsByDate(LocalDate localDate) {
        return this.transactions.stream()
                .filter(t -> t.getDate()
                        .contains(Formatters.dateFormatter.format(localDate))).collect(Collectors.toSet());
    }

    public UUID getCurrencyUuid() {
        return currencyUuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return 0;
    }

    @Override
    public String toString() {
        return "TransactionalCurrency{" +
                "uuid=" + uuid +
                ", currencyUuid=" + currencyUuid +
                ", totalCurrency=" + totalCurrency +
                ", transactions=" + transactions +
                '}';
    }
}
