package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.GameData;

public class Attribute implements GameData {
    private String attribute;
    private Integer magnitude;

    public Attribute(AttributesType attribute, Integer magnitude) {
        this.attribute = attribute.name();
        this.magnitude = magnitude;
    }

    public Attribute(String attribute, Integer magnitude) {
        this.attribute = attribute;
        this.magnitude = magnitude;
    }

    public String getAttribute() {
        return attribute;
    }

    public Integer getMagnitude() {
        return magnitude;
    }

    public void change(Integer delta) {
        int maxValue = Integer.MAX_VALUE - 1;
        if(magnitude + delta > 0 && magnitude + delta < maxValue) {
            this.magnitude = magnitude + delta;
        } else {
            throw new RuntimeException(attribute+ " of value " + magnitude +
                    " cannot have delta of " + delta + " applied" + "\n" + attribute + " " +
                    "value may not be less than 1 or greater than " + maxValue);
        }
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setAttribute(AttributesType attribute) {
        this.attribute = attribute.name();
    }

    public void setMagnitude(Integer magnitude) {
        this.magnitude = magnitude;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "attribute='" + attribute + '\'' +
                ", magnitude=" + magnitude +
                '}';
    }
}
