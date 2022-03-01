package com.firmys.gameservices.world.impl;

public abstract class ItemAbstract implements Item {
    private final String description;
    private final String name;
    private final double weight;
    private final int[] dimensions;

    protected ItemAbstract(String description, String name, double weight, int[] dimensions) {
        this.description = description;
        this.name = name;
        this.weight = weight;
        this.dimensions = dimensions;
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
