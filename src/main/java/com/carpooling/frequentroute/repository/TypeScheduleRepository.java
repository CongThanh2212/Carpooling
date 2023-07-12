package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.TypeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeScheduleRepository extends JpaRepository<TypeSchedule, Integer> {
}
