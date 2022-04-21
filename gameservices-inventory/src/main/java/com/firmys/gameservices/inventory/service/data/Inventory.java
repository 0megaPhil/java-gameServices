package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "INVENTORY")
@Transactional
public class Inventory extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = "uuid", nullable = false, unique = true)
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column(name = "OWNEDITEMS", length = 100000)
    private OwnedItems ownedItems;
    @Column(name = "OWNEDCURRENCIES", length = 10000)
    private OwnedCurrencies ownedCurrencies;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
                "id=" + id +
                ", uuid=" + uuid +
                ", ownedItems=" + ownedItems +
                ", ownedCurrency=" + ownedCurrencies +
                '}';
    }
}
