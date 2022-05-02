package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.Attributes;
import com.firmys.gameservices.common.data.DefaultData;

import javax.persistence.*;
import java.util.UUID;

@Entity
// TODO - Add delete all OwnedItems from all Inventories when Item is deleted
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
    @Column(length = 100000)
    private Attributes requirements;
    @Column
    private int baseValue = DefaultData.DEFAULT_INT;
    @Column(length = 4192)

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        this.requirements = new Attributes();
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


    public Attributes getRequirements() {
        return requirements;
    }

    public void setRequirements(Attributes requirements) {
        this.requirements = requirements;
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
                ", requirements=" + requirements +
                ", baseValue=" + baseValue +
                '}';
    }

}
