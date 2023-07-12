package com.carpooling.frequentroute.model;

import com.github.fabiomaffioletti.firebase.document.FirebaseDocument;
import com.github.fabiomaffioletti.firebase.document.FirebaseId;
import lombok.Data;

@Data
@FirebaseDocument("/user")
public class User {

    @FirebaseId
    private String id;

    private String user_id;
    private String email;
    private String full_name;
    private String username;
    private String profile_photo;
    private String dob;
    private String licence_number;
    private String gender;
    private String registration_plate;
    private String car;
    private String car_photo;
    private String education;
    private String work;
    private String bio;
    private Long mobile_number;
    private int completedRides;
    private int seats;
    private int userRating;
    private int points;
    private Boolean carOwner;
}
