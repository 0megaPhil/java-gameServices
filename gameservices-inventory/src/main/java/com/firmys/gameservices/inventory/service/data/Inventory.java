package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class Inventory extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid;
    @Column(length = 1000000)
    @ElementCollection(targetClass = ConsumableItem.class)
    @OneToMany(mappedBy = ServiceConstants.INVENTORY, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ConsumableItem> consumableItems;
    @Column(length = 1000000)
    @ElementCollection(targetClass = TransactionalCurrency.class)
    @OneToMany(mappedBy = ServiceConstants.INVENTORY, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TransactionalCurrency> transactionalCurrencies;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        consumableItems = ConcurrentHashMap.newKeySet();
        transactionalCurrencies = ConcurrentHashMap.newKeySet();
    }

    public Set<ConsumableItem> getConsumableItems() {
        return consumableItems;
    }

    public void setConsumableItems(Set<ConsumableItem> consumableItems) {
        this.consumableItems = consumableItems;
    }

    public Set<TransactionalCurrency> getTransactionalCurrencies() {
        return transactionalCurrencies;
    }

    public void setTransactionalCurrencies(Set<TransactionalCurrency> transactionalCurrencies) {
        this.transactionalCurrencies = transactionalCurrencies;
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "uuid=" + uuid +
                ", consumableItems=" + consumableItems +
                ", transactionalCurrencies=" + transactionalCurrencies +
                '}';
    }
}
