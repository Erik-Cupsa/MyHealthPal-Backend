package com.my_health_pal.service;

import com.my_health_pal.model.Session;
import com.my_health_pal.model.User;
import com.my_health_pal.repository.SessionRepository;
import com.my_health_pal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, Session updatedSession) {
        Session session = getSessionById(id);
        session.setEndTime(updatedSession.getEndTime());
        session.setCompleted(updatedSession.getCompleted());
        return sessionRepository.save(session);
    }

    public List<Session> getSessionsByUserId(Long userId) {
        return sessionRepository.findSessionByUser_Id(userId);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
