package com.carpooling.frequentroute.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "group_frequent")
public class GroupFrequent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int driver_route_id;

    private Double lat_cur_location;
    private Double lng_cur_location;

    /*
        0: không có khách hàng nào yêu cầu book xe này
        !0: id của request ride mà system gợi ý ghép => Nếu driver chấp nhận cho khách hàng đi chung thì chuyển về giá trị 0
     */
    private int request_ride_id;

    public GroupFrequent() {

    }

    public GroupFrequent(int driver_route_id, Double lat_cur_location, Double lng_cur_location) {
        this.driver_route_id = driver_route_id;
        this.lat_cur_location = lat_cur_location;
        this.lng_cur_location = lng_cur_location;
    }
}
