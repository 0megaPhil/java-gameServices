package com.firmys.gameservice.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservice.common.AbstractGameEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "ITEM")
public class Item extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = "uuid", nullable = false, unique = true)
    @Type(type = "uuid-char")
    private UUID uuid = UUID.randomUUID();
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double weight;
    @Column
    private int length;
    @Column
    private int width;
    @Column
    private int height;
    @Column
    private String requirements;
    @Column(name = "BASEVALUE")
    private int baseValue;

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

    public int getSizeLength() {
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

    public void setSizeLength(int sizeLength) {
        this.length = sizeLength;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", sizeLength=" + length +
                ", sizeWidth=" + width +
                ", sizeHeight=" + height +
                '}';
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }
}
