package com.carpooling.frequentroute.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import lombok.Data;

@Data
@FirebaseDocument("/availableRide")
public class Offer {

    @FirebaseId
    private String id;

    private String rideID;
    private String user_id;
    private String username;
    private String currentLocation;
    private String destination;
    private String dateOfJourney;
    private int seatsAvailable;
    private String licencePlate;
    private double currentlongitude;
    private double currentlatitude;
    private boolean sameGender;
    private int luggageAllowance;
    private String car;
    private String pickupTime;
    private String pickupLocation;
    private int extraTime;
    private String profile_picture;
    private int cost;
    private int completeRides;
    private int userRating;
    private String duration;
}
