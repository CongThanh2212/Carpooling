package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.UUID;

@Entity
@Data
@Table (name = "grid_point")
public class GridPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int grid_trip_id;

    private int lat;

    private int lng;

    private int time;


    @Override
    public String toString(){
        return "(" + lat + ":" + lng + ":" + time +")";
    }
}
