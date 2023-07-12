package com.carpooling.frequentroute.model;

import lombok.Data;

@Data
public class LatLng {

    public double latitude;
    public double longitude;

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean equals(LatLng latLng) {
        if (this.latitude == latLng.latitude && this.longitude == latLng.longitude) {
            return true;
        } else return false;
    }
}
