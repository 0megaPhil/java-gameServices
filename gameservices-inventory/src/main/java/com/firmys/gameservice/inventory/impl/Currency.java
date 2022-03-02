package com.firmys.gameservice.inventory.impl;

public enum Currency {
    GOLD(1, 10, 100, 1000),
    SILVER(.1, 1, 10, 100),
    COPPER(.01, .1, 1, 10),
    TIN(.001, .01, .1, 1);

    private final double goldValue;
    private final double silverValue;
    private final double copperValue;
    private final double tinValue;

    Currency(double goldValue, double silverValue, double copperValue, double tinValue) {
        this.goldValue = goldValue;
        this.silverValue = silverValue;
        this.copperValue = copperValue;
        this.tinValue = tinValue;
    }

    public double getGoldValue() {
        return goldValue;
    }

    public double getSilverValue() {
        return silverValue;
    }

    public double getCopperValue() {
        return copperValue;
    }

    public double getTinValue() {
        return tinValue;
    }

}
