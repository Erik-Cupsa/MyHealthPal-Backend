package com.my_health_pal.repository;

import com.my_health_pal.model.HealthcarePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthcarePlaceRepository extends JpaRepository<HealthcarePlace, Long> {
}
