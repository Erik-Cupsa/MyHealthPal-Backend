package com.my_health_pal.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    public String getChatResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Construct request body
        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));
        request.put("max_tokens", 150);
        request.put("temperature", 0.7);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap in HttpEntity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        // Send POST request
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with OpenAI: " + e.getMessage());
        }
    }
}
