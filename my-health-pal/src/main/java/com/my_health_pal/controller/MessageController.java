package com.my_health_pal.controller;

import com.my_health_pal.model.Message;
import com.my_health_pal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Long id) {
        return ResponseEntity.ok(messageService.getMessageById(id));
    }

    @GetMapping("/session/{id}")
    public ResponseEntity<List<Message>> getMessagesBySession(@PathVariable Long id){
        return ResponseEntity.ok(messageService.getMessagesBySessionId(id));
    }

    @PostMapping("/{sessionId}")
    public ResponseEntity<Message> createMessage(@RequestBody Message message, @PathVariable Long sessionId) {
        return ResponseEntity.ok(messageService.createMessage(message, sessionId));
    }
}
