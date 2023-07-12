package com.carpooling.frequentroute.entity;

import com.carpooling.frequentroute.model.booked.BookedRoute;
import lombok.Data;

import javax.persistence.*;

@SqlResultSetMapping(
        name="bookedMapping",
        classes={
                @ConstructorResult(
                        targetClass= BookedRoute.class,
                        columns={
                                @ColumnResult(name="id", type = int.class),
                                @ColumnResult(name="account_id", type = int.class),
                                @ColumnResult(name="user_name", type = String.class),
                                @ColumnResult(name="address_start", type = String.class),
                                @ColumnResult(name="address_end", type = String.class),
                                @ColumnResult(name="group_id", type = int.class),
                                @ColumnResult(name="time_start", type = String.class),
                                @ColumnResult(name="time_end", type = String.class),
                                @ColumnResult(name="cost", type = double.class),
                                @ColumnResult(name="driver_route_id", type = int.class)
                        }
                )
        }
)
@NamedNativeQuery(name="getAllBookedRouteByAccountId", query="select r.id, a.account_id, a.user_name, r.address_start, r.address_end, r.group_id, r.time_start, r.time_end, r.cost, g.driver_route_id " +
        "from route as r " +
        "inner join account as a on a.account_id = r.account_id " +
        "inner join group_frequent as g on g.id = r.group_id " +
        "where is_shared = 2 and a.account_id = :accountId " +
        "order by str_to_date(time_start, '%H:%i')", resultSetMapping="bookedMapping")
@NamedNativeQuery(name="getAllRideByAccountId", query="select r.id, a.account_id, a.user_name, r.address_start, r.address_end, r.group_id, r.time_start, r.time_end, r.cost, g.driver_route_id " +
        "from route as r " +
        "inner join account as a on a.account_id = r.account_id " +
        "inner join group_frequent as g on g.id = r.group_id " +
        "where r.account_id = :accountId and r.group_id != 0 " +
        "order by str_to_date(time_start, '%H:%i')", resultSetMapping="bookedMapping")

@Entity
@Data
@Table (name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int account_id;

    private int frequent_route_id;

    private String address_start;
    private String address_end;

    private double lat_start;
    private double lng_start;

    private double lat_end;
    private double lng_end;

    private String time_start;
    private String time_end;

    private int is_shared;
    private String type_shared;

    private double length_route;
    private int weekday;

    private int group_id;
    private double cost;

}
