package com.firmys.gameservices.characters.service.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.AbstractGameEntity;
import com.firmys.gameservices.common.ServiceConstants;
import com.firmys.gameservices.common.data.Attributes;
import com.firmys.gameservices.common.data.AttributesType;
import com.firmys.gameservices.common.data.DefaultData;

import javax.persistence.*;
import java.util.Arrays;
import java.util.UUID;

@Entity
public class Character extends AbstractGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonIgnore
    private int id;
    @Column(name = ServiceConstants.UUID, length = 36, nullable = false, unique = true)
    private UUID uuid;
    @Column(unique = true, length = 512)
    private String name;
    @Column(length = 1024)
    private String description;
    @Column
    private String gender;
    @Column
    private int age = DefaultData.DEFAULT_INT;
    @Column
    private int height = DefaultData.DEFAULT_INT;
    @Column
    private int weight = DefaultData.DEFAULT_INT;
    @Column(unique = true)
    private UUID inventoryId;
    @Column(length = 4192)
    private Attributes attributes;

    @PrePersist
    protected void onCreate() {
        uuid = UUID.randomUUID();
        this.attributes = new Attributes();
        Arrays.stream(AttributesType.values())
                .forEach(a -> attributes.initializeAttribute(a, 1));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    /**
     * We use UUID as a unique identifier, and is what we expose, as opposed to primary key
     * @return unique identifier generated when entry is generated
     */
    public UUID getUuid() {
        return uuid;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Character{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", inventoryId=" + inventoryId +
                ", attributes=" + attributes +
                '}';
    }
}
