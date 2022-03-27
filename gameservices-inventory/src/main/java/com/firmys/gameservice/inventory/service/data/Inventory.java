package com.firmys.gameservice.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.firmys.gameservice.common.GameData;
import com.firmys.gameservice.common.JsonUtils;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "INVENTORY")
public class Inventory implements Serializable, GameData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = "uuid", nullable = false, unique = true)
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column(name = "OWNEDITEMS", nullable = true)
    private String ownedItems;
    @Column(name = "Currency")
    private int currency;

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

    public Set<OwnedItem> getOwnedItems() {
        if(ownedItems != null) {
            JavaType type = JsonUtils.getMapper().getTypeFactory().
                    constructCollectionType(Set.class, OwnedItem.class);
            return JsonUtils.mapJsonToObject(ownedItems, type);
        }
        return Collections.emptySet();
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

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", ownedItems='" + ownedItems + '\'' +
                '}';
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }
}
