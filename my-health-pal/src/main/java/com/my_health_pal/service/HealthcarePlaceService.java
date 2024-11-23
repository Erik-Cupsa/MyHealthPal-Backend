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

    public List<HealthcarePlace> getNearbyHealthcarePlaces(double userLat, double userLon, double maxDistance) {
        List<HealthcarePlace> allPlaces = healthcarePlaceRepository.findAll();
        return allPlaces.stream()
                .filter(place -> calculateDistance(userLat, userLon, place.getLatitude(), place.getLongitude()) <= maxDistance)
                .toList();
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

    private double calculateDistance(double userLat, double userLon, double placeLat, double placeLon) {
        final double R = 6371; // Radius of Earth in kilometers
        double dLat = Math.toRadians(placeLat - userLat);
        double dLon = Math.toRadians(placeLon - userLon);
        double lat1 = Math.toRadians(userLat);
        double lat2 = Math.toRadians(placeLat);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
