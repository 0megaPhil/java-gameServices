package com.firmys.gameservices.common.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractAttribute extends AbstractGameEntity implements Attribute {
    protected AttributesType attribute;
    @JsonIgnore
    protected String description;
    protected Integer value;
    @JsonIgnore
    protected Integer minimum;
    @JsonIgnore
    protected Integer maximum;

    public AbstractAttribute(AttributesType attribute, Integer value, Integer minimum, Integer maximum) {
        this.attribute = attribute;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.description = attribute.getDescription();
    }

    public AbstractAttribute() {}

    abstract public UUID getUuid();

    abstract public int getId();

    public AttributesType getAttribute() {
        return attribute;
    }

    public Integer getValue() {
        return value;
    }

    public void setAttribute(AttributesType attribute) {
        this.attribute = attribute;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void shiftValue(Integer delta) {
        if(this.value + delta <= maximum && this.value + delta >= minimum) {
            this.value += delta;
        } else {
            throw new RuntimeException(attribute.name() +
                    " with current value of " + value + " must be between " + minimum + " and " + maximum);
        }
    }

    public void setMinimum(Integer minimum) {
        this.minimum = minimum;
    }

    public void setMaximum(Integer maximum) {
        this.maximum = maximum;
    }

    public Integer getMinimum() {
        return this.minimum;
    }

    public Integer getMaximum() {
        return this.maximum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
