package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.GameData;
import com.firmys.gameservices.common.JsonUtils;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.Type;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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
    @Column(name = "OWNEDCURRENCY", length = 10000)
    private OwnedCurrency ownedCurrency;

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
        //            JavaType type = JsonUtils.getMapper().getTypeFactory()
        //                    .constructCollectionType(Set.class, OwnedItem.class);
        //            return new HashSet<>(JsonUtils.mapJsonToObject(ownedItems, type));
        return Objects.requireNonNullElseGet(ownedItems, OwnedItems::new);
    }

//    public void setOwnedItems(String ownedItems) {
//        this.ownedItems = ownedItems;
//    }

    public void setOwnedItems(OwnedItems ownedItems) {
        this.ownedItems = ownedItems;
//        try {
//            this.ownedItems = JsonUtils.getMapper().writeValueAsString(ownedItems);
//            this.ownedItems = ownedItems;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

    public OwnedCurrency getOwnedCurrency() {
        return Objects.requireNonNullElseGet(ownedCurrency, OwnedCurrency::new);
//        if(ownedCurrency == null) {
//            this.ownedCurrency = JsonUtils.writeObjectAsString(new OwnedCurrency());
//        }
//        JavaType type = JsonUtils.getMapper().getTypeFactory().constructType(OwnedCurrency.class);
//        return JsonUtils.mapJsonToObject(ownedCurrency, type);
    }

//    public void setOwnedCurrency(String ownedCurrency) {
//        this.ownedCurrency = ownedCurrency;
//    }

    public void setOwnedCurrency(OwnedCurrency ownedCurrencies) {
        this.ownedCurrency = ownedCurrencies;
//        this.ownedCurrency = JsonUtils.writeObjectAsString(ownedCurrencies);
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", ownedItems=" + ownedItems +
                ", ownedCurrency=" + ownedCurrency +
                '}';
    }
}
