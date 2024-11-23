package com.my_health_pal.service;

import com.my_health_pal.model.Diagnosis;
import com.my_health_pal.model.Session;
import com.my_health_pal.repository.DiagnosisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    @Autowired
    private GPTService gptService;

    // @Autowired
    // private EmailService emailService;

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

    public Diagnosis generateAndSaveDiagnosis(Session session, String symptoms) {
        // 1. Generate the diagnosis using GPT
        String prompt = "The patient reports the following symptoms: " + symptoms +
                "\nProvide a diagnosis and suggested treatments.";
        String diagnosisText = gptService.getChatResponse(prompt);

        // 2. Save the diagnosis to the database
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setSession(session);
        diagnosis.setDiagnosisText(diagnosisText);
        diagnosis.setCreatedAt(LocalDateTime.now());
        diagnosisRepository.save(diagnosis);

        // 3. Email the diagnosis to the user (commented out for now)
        // String email = session.getUser().getEmail();
        // emailService.sendDiagnosisEmail(email, diagnosisText);

        return diagnosis;
    }
}
