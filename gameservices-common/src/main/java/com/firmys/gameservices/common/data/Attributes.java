package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.GameData;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Attributes implements GameData {
    private Set<Attribute> attributes = ConcurrentHashMap.newKeySet();

    public void initializeAttribute(AttributesType attribute, Integer initialValue) {
        attributes.add(new Attribute(attribute, initialValue));
    }

    public Attributes change(AttributesType attribute, Integer delta) {
        attributes.stream().filter(a -> a.getAttribute().equals(attribute))
                .findFirst().orElseGet(() -> {
                    Attribute newAttr = new Attribute(attribute, 1);
                    attributes.add(newAttr);
                    return newAttr;
                }).change(delta);
        return this;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Attribute getAttribute(AttributesType attribute) {
        return attributes.stream().filter(a -> a.getAttribute().equals(attribute))
                .findFirst().orElseThrow(() -> new RuntimeException("No Attribute " + attribute + " found in " +
                        attributes.stream().map(a -> a.getAttribute().name()).collect(Collectors.toSet())));
    }

    public void setAttributes(Set<Attribute> attributes) {
        this.attributes = attributes;
    }

}
