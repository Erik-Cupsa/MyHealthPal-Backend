package com.my_health_pal.service;

import com.my_health_pal.model.Diagnosis;
import com.my_health_pal.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found with ID: " + id));
    }

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }
}
