package com.carpooling.frequentroute.model;

import lombok.Data;

@Data
public class LatLngGrid {

    private int latitude;
    private int longitude;

    public LatLngGrid(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
