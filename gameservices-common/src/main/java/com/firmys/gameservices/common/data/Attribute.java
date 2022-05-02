package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.GameData;

import java.util.concurrent.atomic.AtomicInteger;

public class Attribute implements GameData {
    private final AttributesType attribute;
    private final AtomicInteger magnitude;

    public Attribute(AttributesType attribute, Integer magnitude) {
        this.attribute = attribute;
        this.magnitude = new AtomicInteger(magnitude);
    }

    public AttributesType getAttribute() {
        return attribute;
    }

    public Integer getMagnitude() {
        return magnitude.get();
    }

    public void change(Integer delta) {
        int maxValue = Integer.MAX_VALUE - 1;
        if(magnitude.get() + delta > 0 && magnitude.get() + delta < maxValue) {
            magnitude.set(magnitude.get() + delta);
        } else {
            throw new RuntimeException(attribute.name() + " of value " + magnitude.get() +
                    " cannot have delta of " + delta + " applied" + "\n" + attribute + " " +
                    "value may not be less than 1 or greater than " + maxValue);
        }
    }
}
