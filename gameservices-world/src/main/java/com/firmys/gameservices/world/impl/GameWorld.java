//package com.firmys.gameservices.world.impl;
//
//import com.firmys.gameservices.world.location.WorldLocation;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
//public class GameWorld {
//
//    private final Map<Coordinate, WorldLocation> worldLocations = new ConcurrentHashMap<>();
//
//    public GameWorld() {
//    }
//
//    public Set<WorldLocation> getLocations() {
//        return new HashSet<>(worldLocations.values());
//    }
//
//    public void addLocation(WorldLocation location) {
//        worldLocations.putIfAbsent(
//                location.getCoordinates(),
//                location);
//    }
//
//    public WorldLocation getLocationByCoordinate(Coordinate coordinate) {
//        return worldLocations.computeIfAbsent(
//                coordinate,
//                v -> new WorldLocation(coordinate, "Default location"));
//    }
//}
