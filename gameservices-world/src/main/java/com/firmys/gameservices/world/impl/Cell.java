package com.firmys.gameservices.world.impl;

import com.firmys.gameservices.world.location.Location;

public interface Cell {
    int getLatitude();
    int getLongitude();
    int getElevation();
    Biome getBiome();
}
