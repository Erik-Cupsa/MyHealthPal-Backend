package com.my_health_pal.controller;

import com.my_health_pal.model.HealthcarePlace;
import com.my_health_pal.service.HealthcarePlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/healthcare-places")
public class HealthcarePlaceController {

    @Autowired
    private HealthcarePlaceService healthcarePlaceService;

    @GetMapping
    public ResponseEntity<List<HealthcarePlace>> getAllHealthcarePlaces() {
        return ResponseEntity.ok(healthcarePlaceService.getAllHealthcarePlaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthcarePlace> getHealthcarePlaceById(@PathVariable Long id) {
        return ResponseEntity.ok(healthcarePlaceService.getHealthcarePlaceById(id));
    }

    @PostMapping
    public ResponseEntity<HealthcarePlace> createHealthcarePlace(@RequestBody HealthcarePlace healthcarePlace) {
        return ResponseEntity.ok(healthcarePlaceService.createHealthcarePlace(healthcarePlace));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthcarePlace(@PathVariable Long id) {
        healthcarePlaceService.deleteHealthcarePlace(id);
        return ResponseEntity.noContent().build();
    }
}
