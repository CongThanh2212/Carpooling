package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.FrequentPoint;
import com.carpooling.frequentroute.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FrequentPointRepostory extends JpaRepository<FrequentPoint, Integer> {

    @Query(value = "select * from frequent_point where frequent_route_id = ?1", nativeQuery = true)
    List<FrequentPoint> findAllByFrequentRoute(int frequent_route_id);

    @Modifying
    @Query(value = "delete from frequent_point where frequent_route_id = ?1", nativeQuery = true)
    void deleteAllByFrequentRoute(int frequent_route_id);

    @Query(value = "select * from frequent_point where frequent_route_id = ?1 and lat = ?2 and lng = ?3", nativeQuery = true)
    FrequentPoint findExpectedTime(int frequent_route_id, int lat, int lng);
}
