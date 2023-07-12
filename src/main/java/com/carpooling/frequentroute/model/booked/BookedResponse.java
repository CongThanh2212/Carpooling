package com.carpooling.frequentroute.model.booked;

import lombok.Data;

@Data
public class BookedResponse {

    private int request_or_route_id;
    private int account_id;
    private String username;
    private int seats;
    private String destination;
    private String origin;
    private int group_id;
    private String pickupTime;
    private String dateOfJourney;
    private String licencePlate;
    private double cost;
    private int capacity;
    private boolean accepted;

    public BookedResponse() {

    }

    public BookedResponse(int request_or_route_id, int account_id, String username, int seats, String destination, String origin, int group_id, String pickupTime, String dateOfJourney, String licencePlate, double cost, int capacity, boolean accepted) {
        this.request_or_route_id = request_or_route_id;
        this.account_id = account_id;
        this.username = username;
        this.seats = seats;
        this.destination = destination;
        this.origin = origin;
        this.group_id = group_id;
        this.pickupTime = pickupTime;
        this.dateOfJourney = dateOfJourney;
        this.licencePlate = licencePlate;
        this.cost = cost;
        this.capacity = capacity;
        this.accepted = accepted;
    }

}
