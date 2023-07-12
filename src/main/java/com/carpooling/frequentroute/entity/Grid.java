package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "grid")
public class Grid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double lat_start;
    private Double lat_end;
    private Double lng_start;
    private Double lng_end;

    private Double anchor_lat;
    private Double anchor_lng;

    public Grid() {

    }

    public Grid(Double lat_start, Double lat_end, Double lng_start, Double lng_end, Double anchor_lat, Double anchor_lng) {
        this.lat_start = lat_start;
        this.lat_end = lat_end;
        this.lng_start = lng_start;
        this.lng_end = lng_end;
        this.anchor_lat = anchor_lat;
        this.anchor_lng = anchor_lng;
    }
}
