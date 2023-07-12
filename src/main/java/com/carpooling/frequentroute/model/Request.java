package com.carpooling.frequentroute.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import lombok.Data;

@Data
@FirebaseDocument("/requestRide")
public class Request {

    @FirebaseId
    private String id;

    private String user_id;
    private String profile_photo;
    private String userProfilePhoto;
    private String username;
    private String dateOfJourney;
    private String pickupTime;
    private Float cost;
    private int seats;
    private String destination;
    private String location;
    private int luggage;
    private Boolean accepted;
    private String ride_id;
    private String pickupLocation;
    private String licencePlate;
}
