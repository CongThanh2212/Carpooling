package com.carpooling.frequentroute.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class RouteDetailsPK implements Serializable {

    private int lat_index;
    private int lng_index;
    private int group_id;

    public RouteDetailsPK(int lat_index, int lng_index, int group_id) {
        this.lat_index = lat_index;
        this.lng_index = lng_index;
        this.group_id = group_id;
    }

    public RouteDetailsPK() {
    }
}
