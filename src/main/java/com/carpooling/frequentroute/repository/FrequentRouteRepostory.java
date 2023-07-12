package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.FrequentRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FrequentRouteRepostory extends JpaRepository<FrequentRoute, Integer> {

    @Query(value = "select * from frequent_route where account_id = ?1", nativeQuery = true)
    List<FrequentRoute> findAllByAccount_id(int account_id);

    @Modifying
    @Query(value = "delete from frequent_route where account_id = ?1", nativeQuery = true)
    void deleteAllByAccount_id(int account_id);

    @Query(value = "select fr.* from frequent_route fr inner join route r on fr.id = r.frequent_route_id where fr.account_id != ?1 and r.is_shared = 1", nativeQuery = true)
    List<FrequentRoute> getFrequentRouteParticipant (int account_id);

    @Query(value = "select fr.* from frequent_route fr inner join route r on fr.id = r.frequent_route_id where fr.account_id != ?1 and r.is_shared = 1 and (r.type_shared = 'Participant' or r.type_shared = ?2)", nativeQuery = true)
    List<FrequentRoute> getFrequentRoutePassengerAndDiver (int account_id,String type_shared);

}
