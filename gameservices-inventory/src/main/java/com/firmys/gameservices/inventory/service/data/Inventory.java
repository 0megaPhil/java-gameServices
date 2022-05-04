package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.ServiceConstants;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.HashSet;
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
    @OneToMany(mappedBy = "inventory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ConsumableItem> consumableItems;
    @Column(length = 1000000)
    @ElementCollection(targetClass = TransactionalCurrency.class)
    @OneToMany(mappedBy = "inventory", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TransactionalCurrency> transactionalCurrencies;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        consumableItems = ConcurrentHashMap.newKeySet();
        transactionalCurrencies = ConcurrentHashMap.newKeySet();
    }

//    public void addConsumableItem(Item item, Integer amount) {
//        if(consumableItems.stream().noneMatch(c -> c.getItemUuid().equals(item.getUuid()))) {
//            consumableItems.add(new ConsumableItem(item));
//        }
//        consumableItems.stream().filter(c ->
//                c.getItemUuid().equals(item.getUuid())).findFirst().orElseThrow().add(amount);
//    }

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
                ", uuid=" + uuid +
                ", ownedItems=" + consumableItems +
                ", ownedCurrency=" + transactionalCurrencies +
                '}';
    }
}
