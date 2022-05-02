package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.Objects;
import java.util.UUID;

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
    private OwnedItems ownedItems;
    @Column(length = 1000000)
    private OwnedCurrencies ownedCurrencies;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public OwnedItems getOwnedItems() {
        return Objects.requireNonNullElseGet(ownedItems, OwnedItems::new);
    }

    public void setOwnedItems(OwnedItems ownedItems) {
        this.ownedItems = ownedItems;
    }

    public OwnedCurrencies getOwnedCurrencies() {
        return Objects.requireNonNullElseGet(ownedCurrencies, OwnedCurrencies::new);
    }

    public void setOwnedCurrencies(OwnedCurrencies ownedCurrencies) {
        this.ownedCurrencies = ownedCurrencies;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                ", uuid=" + uuid +
                ", ownedItems=" + ownedItems +
                ", ownedCurrency=" + ownedCurrencies +
                '}';
    }
}
