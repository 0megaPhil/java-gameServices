package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.DefaultData;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Currency extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid;
    @Column(unique = true, length = 512)
    private String name;
    @Column(length = 4196)
    private String description;
    @Column
    private int baseValue = DefaultData.DEFAULT_INT;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Currency{" +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", baseValue=" + baseValue +
                '}';
    }

}
