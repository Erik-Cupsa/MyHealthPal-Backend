package com.my_health_pal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my_health_pal.model.Message;
import com.my_health_pal.model.Session;
import com.my_health_pal.repository.MessageRepository;
import com.my_health_pal.repository.SessionRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private SentimentService sentimentService;

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

    public Message createTherapyMessage(Message message, Long sessionId) {
        Session session = getSessionById(sessionId);
        String content = message.getContent();
        String pastMessages = findMessagesBySender("User", sessionId);
        if (pastMessages == null) {
            pastMessages = "";
        }
        String updatedMessages = pastMessages + "\n" + content;
        String sentiment = sentimentService.analyzeSentiment(updatedMessages).block();
        String newContent = content + ", The detected sentiment of the user is: " + sentiment;
        message.setContent(newContent);
        message.setSession(session);

        return messageRepository.save(message);
    }

    public Session getSessionById(Long sessionId) {
        return sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + sessionId));
    }

    public String findMessagesBySender(String sender, Long sessionId) {
        List<Message> messages = messageRepository.findMessagesBySession_Id(sessionId);
        StringBuilder allMessages = new StringBuilder();
    
        for (Message message : messages) {
            if (message.getSender().equals(sender)){
                if (allMessages.length() > 0) {
                    allMessages.append("\n");
                }
                allMessages.append(message.getContent());
            }
        }
    
        return allMessages.toString();
    }    
}
