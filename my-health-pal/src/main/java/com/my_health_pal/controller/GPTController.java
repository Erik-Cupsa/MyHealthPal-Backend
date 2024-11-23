package com.my_health_pal.controller;

import com.my_health_pal.service.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
public class GPTController {

    @Autowired
    private GPTService gptService;

    @PostMapping("/chat")
    public ResponseEntity<String> getChatResponse(@RequestBody String prompt) {
        try {
            String response = gptService.getChatResponse(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
