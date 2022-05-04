package com.firmys.gameservices.common.data;

public enum AttributesType {
    STRENGTH("Max Power"),
    PERCEPTION("See all you can see"),
    ENDURANCE("Drink more"),
    CHARISMA("Be liked"),
    INTELLIGENCE("Rapier wit"),
    AGILITY("Dodge responsibility"),
    LUCK("Get lucky"),
    EXPERIENCE("Learn from your mistakes"),
    DEXTERITY("Manipulate things effectively");

    private final String description;

    AttributesType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
