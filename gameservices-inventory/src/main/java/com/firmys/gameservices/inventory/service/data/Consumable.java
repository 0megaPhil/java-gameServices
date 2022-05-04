package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Entity
public class Consumable extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid;
    @ManyToOne
    @JsonIgnore
    private ConsumableItem consumableItem;
    private String consumableType;
    private Long created;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public Consumable() {}

    public Consumable(ConsumableItem consumableItem, String consumableType) {
        this.consumableItem = consumableItem;
        this.consumableType = consumableType;
        LocalDateTime localDateTime = LocalDateTime.now();
        this.created = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getId() {
        return 0;
    }

    public String getConsumableType() {
        return consumableType;
    }

    public void setConsumableType(String consumableType) {
        this.consumableType = consumableType;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public ConsumableItem getConsumableItem() {
        return consumableItem;
    }

    @Override
    public String toString() {
        return "Consumable{" +
                "uuid=" + uuid +
                ", consumableItem=" + consumableItem +
                ", consumableType='" + consumableType + '\'' +
                ", created=" + created +
                '}';
    }
}