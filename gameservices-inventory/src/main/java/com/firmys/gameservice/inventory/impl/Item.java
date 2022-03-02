package com.firmys.gameservice.inventory.impl;

import javax.persistence.*;

@Entity
@Table
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, unique = true)
    private int id;
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
}
