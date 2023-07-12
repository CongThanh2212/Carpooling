package com.carpooling.frequentroute.entity;

import com.carpooling.frequentroute.model.booked.BookedRequestRide;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(
        name = "requestRideMapping",
        classes = {
                @ConstructorResult(
                        targetClass = BookedRequestRide.class,
                        columns = {
                                @ColumnResult(name = "id", type = int.class),
                                @ColumnResult(name = "account_id", type = int.class),
                                @ColumnResult(name = "user_name", type = String.class),
                                @ColumnResult(name = "address_start", type = String.class),
                                @ColumnResult(name = "address_end", type = String.class),
                                @ColumnResult(name = "group_id", type = int.class),
                                @ColumnResult(name = "pick_up_time", type = Double.class),
                                @ColumnResult(name = "cost", type = Double.class),
                                @ColumnResult(name = "capacity", type = int.class),
                                @ColumnResult(name="driver_route_id", type = String.class)
                        }
                )
        }
)
@NamedNativeQuery(name = "getAllBookedRequestByAccountId", query = "select r.id, a.account_id, a.user_name, r.address_start, r.address_end, r.group_id, r.pick_up_time, r.cost, r.capacity, g.driver_route_id " +
        "from request_ride as r " +
        "inner join account as a on a.account_id = r.passenger_id " +
        "inner join group_frequent as g on g.id = r.group_id " +
        "where r.passenger_id = :accountId " +
        "order by pick_up_time", resultSetMapping = "requestRideMapping")

@Entity
@Data
@Table(name = "request_ride")
public class RequestRide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int passenger_id;

    private Double pick_up_time;

    private int capacity;

    private Double cost;

    private Double lat_origin;
    private Double lng_origin;

    private Double lat_destination;
    private Double lng_destination;

    private int status_id;

    private int group_id;

    private String address_start;
    private String address_end;
}
