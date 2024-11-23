package com.my_health_pal.service;
import com.my_health_pal.dto.MessageHistoryDto;
import com.my_health_pal.model.Message;
import com.my_health_pal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GPTService {

    @Autowired
    private MessageService messageService;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;

    public String getChatResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = new HashMap<>();
        request.put("model", "gpt-3.5-turbo");
        request.put("messages", List.of(
                Map.of("role", "user", "content", prompt)
        ));
        request.put("max_tokens", 500);
        request.put("temperature", 0.7);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode content = root.path("choices").get(0).path("message").path("content");

            return content.asText();
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with OpenAI: " + e.getMessage());
        }
    }

    public String getIterativeChatResponse(Long sessionId) {
        List<Message> messages = messageService.getMessagesBySessionId(sessionId);
        List<MessageHistoryDto> messageHistoryDtos = new ArrayList<>();

        for (Message message: messages) {
            messageHistoryDtos.add(MessageHistoryDto.fromEntity(message));
        }

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("This is the conversation history so far:\n\n");
        for (MessageHistoryDto dto : messageHistoryDtos) {
            promptBuilder.append(dto.getSender()).append(": ").append(dto.getContent()).append("\n");
        }

        if (!messageHistoryDtos.isEmpty()) {
            MessageHistoryDto lastMessage = messageHistoryDtos.get(messageHistoryDtos.size() - 1);
            promptBuilder.append("\nFocus on responding to the latest message:\n");
            promptBuilder.append(lastMessage.getContent());
        }

        return getChatResponse(promptBuilder.toString());
    }

}


