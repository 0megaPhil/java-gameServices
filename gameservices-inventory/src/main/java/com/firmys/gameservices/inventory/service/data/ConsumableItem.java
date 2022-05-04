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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO - Add delete all OwnedItems from all Inventories when Item is deleted
@Entity
public class ConsumableItem extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();
    private UUID itemUuid;
    @ManyToOne
    @JsonIgnore
    private Inventory inventory;
    @Column(length = 1000000)
    @ElementCollection(targetClass = Consumable.class)
    @OneToMany(mappedBy = "consumableItem", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Consumable> consumables = ConcurrentHashMap.newKeySet();
    private int ownedCount;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        consumables = ConcurrentHashMap.newKeySet();
    }

    public ConsumableItem(Inventory inventory, Item item) {
        this.inventory = inventory;
        this.itemUuid = item.getUuid();
    }

    public ConsumableItem() {
    }

    public int getOwnedCount() {
        this.ownedCount = consumables.size();
        return ownedCount;
    }

    public void setConsumables(Set<Consumable> consumables) {
        this.consumables = consumables;
    }

    public UUID getItemUuid() {
        return itemUuid;
    }

    public ConsumableItem add() {
        return add(1);
    }

    public ConsumableItem add(int amount) {
        IntStream.range(0, amount)
                .forEach(i -> consumables.add(
                        new Consumable(this, getClass().getSimpleName())
                ));
        this.ownedCount = consumables.size();
        return this;
    }

    public ConsumableItem consume() {
        return consume(1);
    }

    public ConsumableItem consume(int amount) {
        // Actively remove Consumables which do not match ConsumableItem, before consuming
        consumables.removeAll(consumables.stream()
                .filter(c -> !c.getConsumableItem().getUuid().equals(uuid)).collect(Collectors.toSet()));
        ownedCount = consumables.size();
        if(ownedCount < amount) {
            throw new RuntimeException("Insufficient inventory count of " +
                    getOwnedCount() + " for consumption of " + amount + " Item " + itemUuid.toString());
        }
        consumables.removeAll(new HashSet<>(new ArrayList<>(consumables)
                .subList(consumables.size() - (amount + 1), consumables.size() - 1)));
        this.ownedCount = consumables.size();
        return this;
    }

    public void setItemUuid(UUID itemUuid) {
        this.itemUuid = itemUuid;
    }

    public Set<Consumable> getConsumables() {
        return consumables;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return 0;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public String toString() {
        return "ConsumableItem{" +
                "uuid=" + uuid +
                ", itemUuid=" + itemUuid +
                ", inventory=" + inventory +
                ", consumables=" + consumables +
                ", ownedCount=" + ownedCount +
                '}';
    }
}
