package com.carpooling.frequentroute.entity;


import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "route_details")
public class RouteDetails {

    @EmbeddedId
    private RouteDetailsPK id;

    private Double expected_time;

}
