package com.firmys.gameservices.world.location;

import com.firmys.gameservice.inventory.impl.Inventory;
import com.firmys.gameservice.inventory.impl.Item;
import com.firmys.gameservice.inventory.impl.Items;
import com.firmys.gameservice.inventory.impl.LocationInventory;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class LocationAbstract {
    private final Inventory inventory;
    private int[] coordinates;
    private String description;
    private String name;
    private final Set<Character> characters = new HashSet<>();
    private final UUID uuid = UUID.randomUUID();

    public LocationAbstract(int[] coordinates, String description) {
        this.coordinates = coordinates;
        this.description = description;
        this.inventory = new LocationInventory();
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(int[] coordinates) {
        this.coordinates = coordinates;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void addCharacter(Character character) {
        characters.add(character);
    }

    public void removeCharacter(Character character) {
        characters.remove(character);
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public void addItem(Item item, Integer amount) {
        inventory.addOwnedItems(item, amount);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
