package com.my_health_pal.service;

import com.my_health_pal.model.Message;
import com.my_health_pal.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + id));
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
}
