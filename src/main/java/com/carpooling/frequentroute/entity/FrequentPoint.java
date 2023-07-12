package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "frequent_point")
public class FrequentPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int frequent_route_id;

    private int lat;

    private int lng;

    private int time;

    public FrequentPoint() {

    }

    public FrequentPoint(int frequent_route_id, int lat, int lng, int time) {
        this.frequent_route_id = frequent_route_id;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    @Override
    public String toString() {
        return "(" + lat + ":" + lng + ":" + time + ")";
    }
}
