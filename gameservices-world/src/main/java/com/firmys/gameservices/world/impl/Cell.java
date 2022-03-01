package com.firmys.gameservices.world.impl;

import com.firmys.gameservices.world.location.Location;

public interface Cell {
    Location getLocation();
    int[][] getCoordinates();
}
