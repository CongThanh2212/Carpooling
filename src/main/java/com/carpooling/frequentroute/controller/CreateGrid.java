package com.carpooling.frequentroute.controller;

import com.carpooling.frequentroute.entity.FrequentPoint;
import com.carpooling.frequentroute.gripmap.MapUtility;
import com.carpooling.frequentroute.model.LatLng;
import com.carpooling.frequentroute.model.LatLngGrid;
import com.carpooling.frequentroute.repository.EdgeRepository;
import com.carpooling.frequentroute.repository.FrequentPointRepostory;
import com.carpooling.frequentroute.repository.GridRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/create_grid")
public class CreateGrid {

    @Autowired
    private GridRepository gridRepository;

    @Autowired
    private EdgeRepository edgeRepository;

    @Autowired
    private FrequentPointRepostory frequentPointRepostory;

//    @PostMapping("/add")
//    private String addFrequentPoint() {
//        String filePath = "D:\\frequent.txt";
//
//        try (FileReader fileReader = new FileReader(filePath);
//             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//
//            String line;
//            bufferedReader.readLine();
//            while ((line = bufferedReader.readLine()) != null) {
//                String[] first = line.split(",");
//                int frequent_route_id = Integer.parseInt(first[0]);
//                if (frequent_route_id == 3 || frequent_route_id == 4) {
//                    int lat = Integer.parseInt(first[1]);
//                    int lng = Integer.parseInt(first[2]);
//                    int time = Integer.parseInt(first[3]);
//                    frequentPointRepostory.save(new FrequentPoint(frequent_route_id, lat, lng, time));
//                } else {
//                    double lat = Double.parseDouble(first[1]);
//                    double lng = Double.parseDouble(first[2]);
//                    LatLngGrid x = MapUtility.convertToGrid(new LatLng(lat, lng));
//                    int time = Integer.parseInt(first[3]);
//                    frequentPointRepostory.save(new FrequentPoint(frequent_route_id, x.getLatitude(), x.getLongitude(), time));
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "success";
//    }

//    @PostMapping("/grid")
//    @ResponseBody
//    private String createGrid() {
//        double lat = MapUtility.START_LATITUDE;
//        while (lat < MapUtility.END_LATITUDE) {
//            double lng = MapUtility.START_LONGITUDE;
//            double lat_end = Math.round((lat + MapUtility.LENGTH_REGION) * 10000.0) / 10000.0;
//            while (lng < MapUtility.END_LONGITUDE) {
//                Random rd = new Random();
//                int rdLat = rd.nextInt(16) + 5;
//                int rdLng = rd.nextInt(16) + 5;
//                double anchor_lat = Math.round((lat + MapUtility.LENGTH_REGION / rdLat) * 1000000.0) / 1000000.0;
//                double anchor_lng = Math.round((lng + MapUtility.LENGTH_REGION / rdLng) * 1000000.0) / 1000000.0;
//                double lng_end = Math.round((lng + MapUtility.LENGTH_REGION) * 10000.0) / 10000.0;
//                Grid grid = new Grid(lat, lat_end, lng, lng_end, anchor_lat, anchor_lng);
//                gridRepository.save(grid);
//                lng = lng_end;
//            }
//            lat = lat_end;
//        }
//        return "Success";
//    }

//    @PostMapping("/edge")
//    @ResponseBody
//    public String createEdge() {
//        List<Grid> grids = gridRepository.findAll();
//        for (int i = 1; i <= grids.size(); i++) {
//            for (int j = 1; j <= grids.size(); j++) {
//                if (i == j) continue;
//                Grid origin = gridRepository.findById(i).get();
//                Grid des = gridRepository.findById(j).get();
//
//                double lat_anchor_origin = origin.getAnchor_lat();
//                double lng_anchor_origin = origin.getAnchor_lng();
//                double lat_anchor_des = des.getAnchor_lat();
//                double lng_anchor_des = des.getAnchor_lng();
//
//                Random rd = new Random();
//                double rdDis = rd.nextInt(4) + 1;
//                double dis = Math.round((Math.sqrt(Math.pow((lat_anchor_origin - lat_anchor_des), 2.0) + Math.pow((lng_anchor_origin - lng_anchor_des), 2.0)) * 100.0 + rdDis / 2.0) * 10000.0) / 10000.0;
//                double rdDuration = rd.nextInt(7) + 1;
//                double duration = Math.round((dis / 35.0 + rdDuration / 60.0) * 10000.0) / 10000.0;
//                Edge edge = new Edge(i, j, origin.getLat_start(), origin.getLat_end(), origin.getLng_start(), origin.getLng_end(), des.getLat_start(), des.getLat_end(), des.getLng_start(), des.getLng_end(), dis, duration);
//                edgeRepository.save(edge);
//            }
//        }
//
//        return "success";
//    }
}
