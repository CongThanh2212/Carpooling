package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WaypointRepostory extends JpaRepository<Waypoint, Integer> {
    @Query(value = "select * from waypoint where on_trip = ?1", nativeQuery = true)
    List<Waypoint> findAllByOn_trip(int on_trip);
}
