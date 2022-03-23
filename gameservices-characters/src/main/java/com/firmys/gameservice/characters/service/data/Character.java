package com.firmys.gameservice.characters.service.data;

import com.firmys.gameservice.inventory.impl.PlayerCharacterInventory;

import javax.persistence.*;

@Entity
@Table
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String gender;
    @Column
    private double height;
    @Column
    private double weight;
    @Column
    private PlayerCharacterInventory inventory;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getGender() {
        return gender;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public PlayerCharacterInventory getInventory() {
        return inventory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setInventory(PlayerCharacterInventory inventory) {
        this.inventory = inventory;
    }
}
