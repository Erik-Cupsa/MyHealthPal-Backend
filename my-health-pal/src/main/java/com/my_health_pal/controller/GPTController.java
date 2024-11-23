package com.my_health_pal.controller;

import com.my_health_pal.model.Message;
import com.my_health_pal.service.GPTService;
import com.my_health_pal.service.MessageService;
import com.my_health_pal.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gpt")
public class GPTController {

    @Autowired
    private GPTService gptService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/chat")
    public ResponseEntity<String> getChatResponse(@RequestBody String prompt) {
        try {
            String response = gptService.getChatResponse(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/chat/{sessionId}")
    public ResponseEntity<Message> getIterativeChatResponse(@PathVariable Long sessionId) {
        try {
            Message response = gptService.getIterativeChatResponse(sessionId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Message errorMessage = new Message();
            errorMessage.setSender("system");
            errorMessage.setContent("An error occurred while processing the response: " + e.getMessage());
            return ResponseEntity.status(500).body(errorMessage);
        }
    }
}
