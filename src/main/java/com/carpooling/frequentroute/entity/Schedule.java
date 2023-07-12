package com.carpooling.frequentroute.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int group_id;

    private int passenger_id;

    private Double lat;
    private Double lng;

    // value khi schedule là request thông thường k phải frequent = vô cùng. Vì đặt xe thông thường chỉ require pick up time
    private Double time_late;

    private Double expected_time;

    private int location_id;

    private int schedule_id;

    private int capacity_available;

    public Schedule(int group_id, int passenger_id, Double lat, Double lng, Double time_late, Double expected_time, int location_id, int schedule_id, int capacity_available) {
        this.group_id = group_id;
        this.passenger_id = passenger_id;
        this.lat = lat;
        this.lng = lng;
        this.time_late = time_late;
        this.expected_time = expected_time;
        this.location_id = location_id;
        this.schedule_id = schedule_id;
        this.capacity_available = capacity_available;
    }
}
