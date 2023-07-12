package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "edge")
public class Edge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int origin;
    private int destination;

    private Double lat_start_origin;
    private Double lat_end_origin;
    private Double lng_start_origin;
    private Double lng_end_origin;

    private Double lat_start_des;
    private Double lat_end_des;
    private Double lng_start_des;
    private Double lng_end_des;

    private Double distance;
    private Double duration;

    public Edge() {

    }

    public Edge(int origin, int destination, Double lat_start_origin, Double lat_end_origin, Double lng_start_origin, Double lng_end_origin, Double lat_start_des, Double lat_end_des, Double lng_start_des, Double lng_end_des, Double distance, Double duration) {
        this.origin = origin;
        this.destination = destination;
        this.lat_start_origin = lat_start_origin;
        this.lat_end_origin = lat_end_origin;
        this.lng_start_origin = lng_start_origin;
        this.lng_end_origin = lng_end_origin;
        this.lat_start_des = lat_start_des;
        this.lat_end_des = lat_end_des;
        this.lng_start_des = lng_start_des;
        this.lng_end_des = lng_end_des;
        this.distance = distance;
        this.duration = duration;
    }
}
