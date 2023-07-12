package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Route;
import com.carpooling.frequentroute.entity.RoutePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoutePointRepository extends JpaRepository<RoutePoint, Integer> {
    @Query(value = "select * from route_point where route_id = ?1", nativeQuery = true)
    List<RoutePoint> findAllByRoute_id(int route_id);
}
