package com.firmys.gameservices.world.location;

import com.firmys.gameservice.inventory.impl.Inventory;
import com.firmys.gameservice.inventory.impl.Items;
import com.firmys.gameservices.world.impl.Biome;
import com.firmys.gameservices.world.impl.Coordinate;
import reactor.util.function.Tuple3;

import java.util.Set;
import java.util.UUID;

public interface Location {

    Coordinate getCoordinates();
    void setCoordinates(Coordinate coordinates);
    String getDescription();
    String getName();
    UUID getUuid();
    void addCharacter(Character character);
    void removeCharacter(Character character);
    Set<Character> getCharacters();
    void addItem(Items item, Integer amount);
    void setDescription(String description);
    void setName(String name);
    Inventory getInventory();
    Integer getLatitude();
    Integer getLongitude();
    Integer getElevation();
    Biome getBiome();
}
