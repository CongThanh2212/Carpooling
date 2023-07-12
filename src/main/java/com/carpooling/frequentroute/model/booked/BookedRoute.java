package com.carpooling.frequentroute.model.booked;

import lombok.Data;

@Data
public class BookedRoute {

    private int id;
    private int account_id;
    private String user_name;
    private String address_start;
    private String address_end;
    private int group_id;
    private String time_start;
    private String time_end;
    private double cost;
    private int driver_route_id;

    public BookedRoute() {

    }

    public BookedRoute(int id, int account_id, String user_name, String address_start, String address_end, int group_id, String time_start, String time_end, double cost, int driver_route_id) {
        this.id = id;
        this.account_id = account_id;
        this.user_name = user_name;
        this.address_start = address_start;
        this.address_end = address_end;
        this.group_id = group_id;
        this.time_start = time_start;
        this.time_end = time_end;
        this.cost = cost;
        this.driver_route_id = driver_route_id;
    }
}
