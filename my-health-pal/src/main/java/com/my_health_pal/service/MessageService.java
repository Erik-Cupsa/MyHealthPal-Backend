package com.my_health_pal.service;

import com.my_health_pal.model.Message;
import com.my_health_pal.model.Session;
import com.my_health_pal.model.User;
import com.my_health_pal.repository.MessageRepository;
import com.my_health_pal.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Message not found with ID: " + id));
    }

    public List<Message> getMessagesBySessionId(Long sessionId) {
        return messageRepository.findMessagesBySession_Id(sessionId);
    }

    public Message createMessage(Message message, Long sessionId) {
        Session session = getSessionById(sessionId);
        message.setSession(session);

        return messageRepository.save(message);
    }

    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + sessionId));
    }
}
