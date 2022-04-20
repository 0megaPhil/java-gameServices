package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.JsonUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "INVENTORY")
public class Inventory extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = "uuid", nullable = false, unique = true)
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column(name = "OWNEDITEMS")
    private String ownedItems;
    @Column(name = "OWNEDCURRENCY")
    private String ownedCurrency;

    public int getId() {
        return id;
    }

    public void update(GameData gameData) {
        Inventory inventory = (Inventory) gameData;
        this.setOwnedCurrency(inventory.getOwnedCurrency());
        this.setOwnedItems(inventory.getOwnedItems());
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

    public Set<OwnedItem> getOwnedItems() {
        if(ownedItems != null) {
            JavaType type = JsonUtils.getMapper().getTypeFactory()
                    .constructCollectionType(Set.class, OwnedItem.class);
            return new HashSet<>(JsonUtils.mapJsonToObject(ownedItems, type));
        }
        return new HashSet<>();
    }

    public void setOwnedItems(String ownedItems) {
        this.ownedItems = ownedItems;
    }

    public void setOwnedItems(Set<OwnedItem> ownedItems) {
        try {
            this.ownedItems = JsonUtils.getMapper().writeValueAsString(ownedItems);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public OwnedCurrency getOwnedCurrency() {
        if(ownedCurrency == null) {
            this.ownedCurrency = JsonUtils.writeObjectAsString(new OwnedCurrency());
        }
        JavaType type = JsonUtils.getMapper().getTypeFactory().constructType(OwnedCurrency.class);
        return JsonUtils.mapJsonToObject(ownedCurrency, type);
    }

    public void setOwnedCurrency(String ownedCurrency) {
        this.ownedCurrency = ownedCurrency;
    }

    public void setOwnedCurrency(OwnedCurrency ownedCurrencies) {
        this.ownedCurrency = JsonUtils.writeObjectAsString(ownedCurrencies);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", ownedItems='" + ownedItems + '\'' +
                ", ownedCurrency='" + ownedCurrency + '\'' +
                '}';
    }
}
