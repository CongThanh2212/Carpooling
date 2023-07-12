package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "type_is_shared")
public class TypeIsShared {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /*
        0: chưa share
        1: đã share nhưng chưa đc ghép / đã đc ghép nhưng là driver
        2: đã đc ghép và đc system chỉ định vai trò trong chuyến đi là passenger
     */
    private String type;
}
