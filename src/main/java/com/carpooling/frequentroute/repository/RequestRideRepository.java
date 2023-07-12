package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.RequestRide;
import com.carpooling.frequentroute.model.booked.BookedRequestRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRideRepository extends JpaRepository<RequestRide, Integer> {

    @Modifying
    @Query(value = "update request_ride set status_id = ?1 where group_id = ?2", nativeQuery = true)
    void updateStatus(int statusId, int groupId);

    @Query(value = "select * from request_ride where group_id = ?1", nativeQuery = true)
    List<RequestRide> findAllByGroupId(int groupId);

    @Query(value = "select * from request_ride where group_id != 0 and passenger_id = ?1", nativeQuery = true)
    List<RequestRide> getAllBookedByAccountId(int accountId);

    @Query(nativeQuery = true, name = "getAllBookedRequestByAccountId")
    List<BookedRequestRide> getAllBookedRequestByAccountId(@Param("accountId") int accountId);
}
