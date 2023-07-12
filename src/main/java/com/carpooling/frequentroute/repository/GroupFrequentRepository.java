package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.GroupFrequent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupFrequentRepository extends JpaRepository<GroupFrequent, Integer> {

    @Query(value = "select * from group_frequent where id = ?1", nativeQuery = true)
    GroupFrequent findById(int group_id);

    @Modifying
    @Query(value = "update group_frequent set capacity_frequent = capacity_frequent - 1 where id = ?1", nativeQuery = true)
    void updateCapacity(int group_id);

}
