package com.firmys.gameservice.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservice.common.GameData;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "ITEM")
public class Item implements Serializable, GameData {
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
    private int sizeLength;
    @Column
    private int sizeWidth;
    @Column
    private int sizeHeight;

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
        return sizeLength;
    }

    public int getSizeWidth() {
        return sizeWidth;
    }

    public int getSizeHeight() {
        return sizeHeight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void update(Item updated) {
        this.setDescription(updated.getDescription());
        this.setSizeHeight(updated.getSizeHeight());
        this.setSizeLength(updated.getSizeLength());
        this.setSizeWidth(updated.getSizeWidth());
        this.setWeight(updated.getWeight());
        this.setName(updated.getName());
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", weight=" + weight +
                ", sizeLength=" + sizeLength +
                ", sizeWidth=" + sizeWidth +
                ", sizeHeight=" + sizeHeight +
                '}';
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
        this.sizeLength = sizeLength;
    }

    public void setSizeWidth(int sizeWidth) {
        this.sizeWidth = sizeWidth;
    }

    public void setSizeHeight(int sizeHeight) {
        this.sizeHeight = sizeHeight;
    }
}
