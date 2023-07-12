package com.carpooling.frequentroute.entity;

// import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table (name = "trip")
public class Trip implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trip_id;

    private int account_owner;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int weekday;


}
