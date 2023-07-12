package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Edge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EdgeRepository extends JpaRepository<Edge, Integer> {

    @Query(value = "select * from edge " +
            "where lat_start_origin < ?1, lat_end_origin >= ?1, lng_start_origin < ?2, lng_end_origin >= ?2, " +
            "lat_start_des < ?3, lat_end_des >= ?3, lng_start_des < ?4, lng_end_des >= ?4", nativeQuery = true)
    Edge getDuration(double lat_origin, double lng_origin, double lat_destination, double lng_destination);
}
