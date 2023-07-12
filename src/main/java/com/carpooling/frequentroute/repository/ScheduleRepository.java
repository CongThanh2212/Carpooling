package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query(value = "select * from schedule where group_id = ?1 and expected_time < ?2 order by expected_time desc limit 1", nativeQuery = true)
    Schedule findByGroupAndSort(int groupId, double expectedTime);

    @Query(value = "select * from schedule where group_id = ?1 and expected_time > ?2 and expected_time < ?3", nativeQuery = true)
    List<Schedule> findBetweenOriginAndDes(int groupId, double expectedTimeOrigin, double expectedTimeDes);

    @Modifying
    @Query(value = "update schedule set capacity_available = capacity_available - 1 where group_id = ?1 and expected_time > ?2 and expected_time < ?3", nativeQuery = true)
    void updateCapacity(int groupId, double expectedTimeOrigin, double expectedTimeDes);

    @Modifying
    @Query(value = "update schedule set capacity_available = capacity_available - ?1, time_late = time_late - ?2, expected_time = expected_time + ?2 where id = ?3 ", nativeQuery = true)
    void updateCapacityAndTime(int capacity, double deltaTime, int scheduleId);

    @Modifying
    @Query(value = "delete from schedule where group_id = ?1 and passenger_id = ?2", nativeQuery = true)
    void deleteOriginAndDestination(int groupId, int passengerId);

    @Query(value = "select * from schedule where group_id = ?1 order by expected_time", nativeQuery = true)
    List<Schedule> getByGroupId(int groupId);
}
