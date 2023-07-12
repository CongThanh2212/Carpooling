package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TripRepostory extends JpaRepository<Trip, Integer> {

    @Query(value = "select * from trip where account_owner = ?1", nativeQuery = true)
    List<Trip> findAllByAccount_owner(int account_id);

    @Query(value = "select * from trip where account_owner = ?1 and date = ?2", nativeQuery = true)
    List<Trip> findAllByAccount_ownerAndDate(int account_id, Date date);


}
