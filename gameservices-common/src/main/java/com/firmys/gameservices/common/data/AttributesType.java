package com.firmys.gameservices.common.data;

public enum AttributesType {
    STRENGTH(""),
    PERCEPTION(""),
    ENDURANCE(""),
    CHARISMA(""),
    INTELLIGENCE(""),
    AGILITY(""),
    LUCK(""),
    EXPERIENCE(""),
    DEXTERITY("");

    private final String description;

    AttributesType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
