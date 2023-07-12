package com.carpooling.frequentroute.model.ride;

import lombok.Data;

@Data
public class RideResponse {

    private int rideID;
    private String username;
    private String origin;
    private String destination;
    private String dateOfJourney;
    private String pickupTime;
    private int seat;
    private int account_id;

    public RideResponse() {

    }

    public RideResponse(int rideID, String username, String origin, String destination, String dateOfJourney, String pickupTime, int seat, int account_id) {
        this.rideID = rideID;
        this.username = username;
        this.origin = origin;
        this.destination = destination;
        this.dateOfJourney = dateOfJourney;
        this.pickupTime = pickupTime;
        this.seat = seat;
        this.account_id = account_id;
    }
}
