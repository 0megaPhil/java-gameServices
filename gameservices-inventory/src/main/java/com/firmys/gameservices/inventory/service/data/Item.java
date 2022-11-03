package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.AttributesType;
import com.firmys.gameservices.common.data.DefaultData;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Entity
public class Item extends AbstractGameEntity {
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
    private double weight;
    @Column
    private int length = DefaultData.DEFAULT_INT;
    @Column
    private int width = DefaultData.DEFAULT_INT;
    @Column
    private int height = DefaultData.DEFAULT_INT;
    @Column
    private int baseValue = DefaultData.DEFAULT_INT;
    @Column(updatable = false)
    @OneToMany(mappedBy = ServiceConstants.ITEM, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<ItemRequirement> itemRequirements;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        itemRequirements = ConcurrentHashMap.newKeySet();
        Arrays.stream(AttributesType.values()).forEach(a ->
                itemRequirements.add(new ItemRequirement(this, a, 1)));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setLength(int sizeLength) {
        this.length = sizeLength;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    @Override
    public String toString() {
        return "Item{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", baseValue=" + baseValue +
                '}';
    }

    public Set<ItemRequirement> getItemRequirements() {
        return itemRequirements;
    }

    public void setItemRequirements(Set<ItemRequirement> itemRequirements) {
        this.itemRequirements = itemRequirements;
    }
}
