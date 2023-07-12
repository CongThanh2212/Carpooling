package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table (name = "grid_trip")
public class GridTrip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grid_trip_id")
    private int id;

    @Column(name = "account_id")
    private int account_id;

    @Column(name = "trip_id")
    private int trip_id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int weekday;

}
