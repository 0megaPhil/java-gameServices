package com.firmys.gameservices.inventory.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.AbstractAttribute;
import com.firmys.gameservices.common.data.AttributesType;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class ItemRequirement extends AbstractAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, updatable = false, nullable = false, unique = true)
    private UUID uuid;
    @JsonIgnore
    @ManyToOne
    private Item item;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
    }

    public ItemRequirement(Item item, AttributesType attribute, Integer value) {
        super(attribute, value, 1, 255);
        this.item = item;
    }

    public ItemRequirement() {
        super();
    }

    public int getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "CharacterAttribute{" +
                "uuid=" + uuid +
                ", attribute=" + attribute +
                ", description='" + description + '\'' +
                ", value=" + value +
                ", minimum=" + minimum +
                ", maximum=" + maximum +
                '}';
    }
}
