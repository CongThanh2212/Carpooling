package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.GridPoint;
import com.carpooling.frequentroute.entity.GridTrip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GridPointRepostory extends JpaRepository<GridPoint, Integer> {

    @Query(value = "select * from grid_point where grid_trip_id = ?1", nativeQuery = true)
    List<GridPoint> findAllByGrid_trip_id(int grid_trip_id);
}
