package com.firmys.gameservices.common.data;

import com.firmys.gameservices.common.GameData;

public interface Attribute extends GameData {
    AttributesType getAttribute();
    void setAttribute(AttributesType attribute);
    String getDescription();
    void setDescription(String description);
    Integer getValue();
    void setValue(Integer magnitude);
    void shiftValue(Integer delta);
}
