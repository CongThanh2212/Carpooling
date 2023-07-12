package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table (name = "route_point")
public class RoutePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int route_id;

    double lat;
    double lng;
}
