package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.ErrorMessage;
import com.carpooling.frequentroute.entity.Trip;
import com.carpooling.frequentroute.entity.Waypoint;
import com.carpooling.frequentroute.repository.GridTripRepostory;
import com.carpooling.frequentroute.repository.TripRepostory;
import com.carpooling.frequentroute.repository.WaypointRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExectionController {
    @ExceptionHandler(Exception.class)
    public ErrorMessage TodoException(Exception ex, WebRequest request) {
        return new ErrorMessage(10100, ex.getLocalizedMessage());
    }

}
