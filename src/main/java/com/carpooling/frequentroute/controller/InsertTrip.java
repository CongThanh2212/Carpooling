package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.repository.TripRepostory;
import com.carpooling.frequentroute.repository.WaypointRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/insert")
public class InsertTrip {

    @Autowired
    private TripRepostory tripRepostory;

    @Autowired
    private WaypointRepostory waypointRepostory;

//    @PostMapping("/waypoint")
//    @ResponseBody
//    public String insertWaypoint() {
//        for (int i = 1; i < 101; i++) {
//            String filePath = "D:\\taxi_log_2008_by_id\\";
//            filePath = filePath + i + ".txt";
//
//            try (FileReader fileReader = new FileReader(filePath);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//
//                String line;
//                while ((line = bufferedReader.readLine()) != null) {
//                    String[] first = line.split(",");
//                    double lng = Double.parseDouble(first[2]);
//                    double lat = Double.parseDouble(first[3]);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }
}
