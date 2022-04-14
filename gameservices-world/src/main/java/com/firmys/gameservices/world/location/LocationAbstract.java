//package com.firmys.gameservices.world.location;
//
//import com.firmys.gameservice.inventory.impl.Inventory;
//import com.firmys.gameservice.inventory.service.data.Item;
//import com.firmys.gameservice.inventory.impl.Items;
//import com.firmys.gameservice.inventory.impl.LocationInventory;
//import com.firmys.gameservices.world.impl.Biome;
//import com.firmys.gameservices.world.impl.Coordinate;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//public abstract class LocationAbstract implements Location {
//    private final Inventory inventory;
//    private Coordinate coordinates;
//    private String description;
//    private String name;
//    private final Set<Character> characters = new HashSet<>();
//    private final UUID uuid = UUID.randomUUID();
//
//    public LocationAbstract(Coordinate coordinates, String description) {
//        this.coordinates = coordinates;
//        this.description = description;
//        this.inventory = new LocationInventory();
//    }
//
//    public Coordinate getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(Coordinate coordinates) {
//        this.coordinates = coordinates;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void addItem(Items item, Integer amount) {
//
//    }
//
//    public UUID getUuid() {
//        return uuid;
//    }
//
//    public void addCharacter(Character character) {
//        characters.add(character);
//    }
//
//    public void removeCharacter(Character character) {
//        characters.remove(character);
//    }
//
//    public Set<Character> getCharacters() {
//        return characters;
//    }
//
//    public void addItem(Item item, Integer amount) {
//        inventory.addOwnedItems(item, amount);
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Inventory getInventory() {
//        return inventory;
//    }
//
//    public Integer getLatitude() {
//        return coordinates.getLatitude();
//    }
//
//    public Integer getLongitude() {
//        return coordinates.getLongitude();
//    }
//
//    public Integer getElevation() {
//        return coordinates.getElevation();
//    }
//
//    public Biome getBiome() {
//        return coordinates.getBiome();
//    }
//}
