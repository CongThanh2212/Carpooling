package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.RouteDetails;
import com.carpooling.frequentroute.entity.RouteDetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteDetailsRepository extends JpaRepository<RouteDetails, RouteDetailsPK> {

    @Modifying
    @Query(value = "delete from route_details where group_id = ?1", nativeQuery = true)
    void deleteByGroupId(int groupId);
}
