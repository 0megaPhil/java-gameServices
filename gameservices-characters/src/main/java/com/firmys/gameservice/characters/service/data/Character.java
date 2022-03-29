package com.firmys.gameservice.characters.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservice.common.GameData;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "CHARACTER")
public class Character implements Serializable, GameData {
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
    private String gender;
    @Column
    private int age;
    @Column
    private int height;
    @Column
    private int weight;
    @Column(name = "INVENTORYID", unique = true)
    private UUID inventoryId;

    public int getId() {
        return id;
    }

    @Override
    public void update(GameData gameData) {
        Character character = (Character) gameData;
        this.setDescription(character.getDescription());
        this.setGender(character.getGender());
        this.setWeight(character.getWeight());
        this.setName(character.getName());
        this.setHeight(character.getHeight());
        this.setAge(character.getAge());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * We use UUID as a unique identifier, and is what we expose, as opposed to primary key
     * @return unique identifier generated when entry is generated
     */
    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
