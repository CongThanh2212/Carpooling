package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "request_status")
public class RequestStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*
        1: not serve
        2: serve
        3: cancel
     */
    private String type;
}
