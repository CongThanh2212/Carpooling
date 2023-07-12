package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table (name = "account")
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int account_id;

    @Column(name = "user_name")
    private String user_name;

    private String full_name;

    private String phone;

    private String password;

    private String license_plate;

    private int seat;

    private String name_car;

    public Account() {

    }

    public Account(int account_id, String user_name) {
        this.account_id = account_id;
        this.user_name = user_name;
    }
}
