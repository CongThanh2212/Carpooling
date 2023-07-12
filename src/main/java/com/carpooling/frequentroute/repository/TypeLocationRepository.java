package com.carpooling.frequentroute.repository;

import com.carpooling.frequentroute.entity.TypeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeLocationRepository extends JpaRepository<TypeLocation, Integer> {
}
