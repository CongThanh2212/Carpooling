package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.Grid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridRepository extends JpaRepository<Grid, Integer> {
}
