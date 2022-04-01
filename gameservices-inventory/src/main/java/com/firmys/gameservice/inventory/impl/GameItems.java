package com.firmys.gameservice.inventory.impl;

import com.firmys.gameservice.inventory.service.data.Item;

public abstract class GameItems implements Items {

    private final String description;
    private final String name;
    private final double weight;
    private final int[] dimensions;

    protected GameItems(String description, String name, double weight, int[] dimensions) {
        this.description = description;
        this.name = name;
        this.weight = weight;
        this.dimensions = dimensions;
    }

    protected GameItems(Item item) {
        this.description = item.getDescription();
        this.name = item.getName();
        this.weight = item.getWeight();
        this.dimensions = new int[]{item.getSizeLength(),
                item.getWidth(), item.getHeight()};
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Double getWeight() {
        return weight;
    }

    @Override
    public int[] getDimensions() {
        return dimensions;
    }
}
