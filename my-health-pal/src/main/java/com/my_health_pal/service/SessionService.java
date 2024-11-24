package com.my_health_pal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.my_health_pal.model.Message;
import com.my_health_pal.model.Session;
import com.my_health_pal.model.User;
import com.my_health_pal.repository.SessionRepository;
import com.my_health_pal.repository.UserRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
    }

    public Session createSession(Session session, Long userId) {
        User user = getUserById(userId);
        session.setUser(user);

        Session sessionReturned = sessionRepository.save(session);

        String preContent = "You are a virtual medical assistant. Your primary goal is to gather as much relevant information as possible to understand the user's symptoms, concerns, and health history before recommending next steps.\n" +
                "\n" +
                "1. Always start by asking clarifying questions about their symptoms, such as duration, severity, and any triggers or related factors. \n" +
                "2. Do not provide any explanations or recommendations until you have gathered sufficient information through detailed questioning.\n" +
                "3. Once you have enough information, provide a clear explanation of possible causes or conditions in simple, non-technical language.\n" +
                "4. Only suggest seeing a doctor or visiting a medical clinic if you believe the issue might require professional medical attention. In such cases, include this exact phrase in your response: \"This issue requires medical attention.\"\n" +
                "5. Be empathetic, assertive, and professional throughout the conversation. Encourage the user to provide as much detail as they can.\n" +
                "\n" +
                "Always ensure the user feels heard and understood. Begin every response with questions to gather more information before proceeding with advice or suggestions.\n";


        Message preMessage = new Message();
        preMessage.setContent(preContent);
        preMessage.setSender("user");

        messageService.createMessage(preMessage, sessionReturned.getId());

        String gender = user.getGender();
        Integer age = user.getAge();
        String medicalHistory = user.getMedicalHistory();

        String content = "The user being diagnosed has the following details: " +
                "Gender: " + gender + ", " +
                "Age: " + age + ", " +
                "Medical History: " + medicalHistory + ".";

        Message message = new Message();
        message.setContent(content);
        message.setSender("user");

        messageService.createMessage(message, sessionReturned.getId());

        return sessionRepository.save(session);
    }

    public Session createTherapySession(Session session) {
        Session sessionReturned = sessionRepository.save(session);

        String content = "You are a simulated therapist. Your primary goal is to provide empathetic, thoughtful, and helpful responses tailored to the user's feelings.\n" +
                "\n" +
                "1. Always start by acknowledging the user's feelings explicitly. Ensure the user feels validated and understood.\n" +
                "   - If the user feels 'happy', encourage them to share more about their positive experience and explore what contributes to their happiness.\n" +
                "   - If the user feels 'sad', provide supportive and empathetic responses that help them feel heard. Encourage them to express what might be causing their sadness.\n" +
                "   - If the user feels 'neutral', ask open-ended questions to better understand their current state or guide the conversation towards a meaningful discussion.\n" +
                "2. Tailor your responses and tone to match the user's feelings while maintaining professionalism and warmth.\n" +
                "3. Use active listening techniques in your responses by paraphrasing their concerns or feelings to show you understand.\n" +
                "4. Avoid giving direct advice unless explicitly requested; instead, guide the user to reflect on their thoughts and feelings to find clarity.\n" +
                "5. Be empathetic, supportive, and non-judgmental throughout the conversation. Foster a safe and comfortable environment for the user to express themselves fully.\n" +
                "\n" +
                "Always ensure the user feels heard, validated, and supported. Begin every response by addressing their expressed feeling and build your response from there.";


        Message message = new Message();
        message.setContent(content);
        message.setSender("user");

        messageService.createMessage(message, sessionReturned.getId());

        return sessionReturned;
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
