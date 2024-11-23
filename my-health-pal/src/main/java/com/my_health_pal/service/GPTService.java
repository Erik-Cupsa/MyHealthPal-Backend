package com.my_health_pal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class GPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/completions}")
    private String apiUrl;

    public String getChatResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("model", "text-davinci-003"); // Change the model as needed
        request.put("prompt", prompt);
        request.put("max_tokens", 150);
        request.put("temperature", 0.7);

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + apiKey);
        headers.put("Content-Type", "application/json");

        // Send POST request to OpenAI API
        try {
            return restTemplate.postForObject(apiUrl, request, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with OpenAI: " + e.getMessage());
        }
    }
}
