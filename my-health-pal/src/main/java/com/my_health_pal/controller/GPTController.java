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
    public ResponseEntity<String> getIterativeChatResponse(@PathVariable Long sessionId) {
        try {
            String response = gptService.getIterativeChatResponse(sessionId);
            Message message = new Message();
            message.setContent(response);
            message.setSender("ChatGPT");

            messageService.createMessage(message, sessionId);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
