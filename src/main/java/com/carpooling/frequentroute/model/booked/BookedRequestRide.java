package com.carpooling.frequentroute.model.booked;

import lombok.Data;

import java.util.Date;

@Data
public class BookedRequestRide {

    private int id;
    private int account_id;
    private String user_name;
    private String address_start;
    private String address_end;
    private int group_id;
    private Date pick_up_time;
    private double cost;
    private int capacity;
    private int driver_route_id;

    public BookedRequestRide() {

    }

    public BookedRequestRide(int id, int account_id, String user_name, String address_start, String address_end, int group_id, Date pick_up_time, double cost, int capacity, int driver_route_id) {
        this.id = id;
        this.account_id = account_id;
        this.user_name = user_name;
        this.address_start = address_start;
        this.address_end = address_end;
        this.group_id = group_id;
        this.pick_up_time = pick_up_time;
        this.cost = cost;
        this.capacity = capacity;
        this.driver_route_id = driver_route_id;
    }
}
