package com.my_health_pal.service;

import com.my_health_pal.model.HealthcarePlace;
import com.my_health_pal.repository.HealthcarePlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HealthcarePlaceService {

    @Autowired
    private HealthcarePlaceRepository healthcarePlaceRepository;

    public List<HealthcarePlace> getAllHealthcarePlaces() {
        return healthcarePlaceRepository.findAll();
    }

    public HealthcarePlace getHealthcarePlaceById(Long id) {
        return healthcarePlaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Healthcare place not found with ID: " + id));
    }

    public HealthcarePlace createHealthcarePlace(HealthcarePlace healthcarePlace) {
        return healthcarePlaceRepository.save(healthcarePlace);
    }

    public void deleteHealthcarePlace(Long id) {
        healthcarePlaceRepository.deleteById(id);
    }
}
