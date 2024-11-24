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

        String content = "You are a simulated conversational therapist. Your primary goal is to provide thoughtful and engaging responses that encourage open dialogue and help the user explore their thoughts and experiences.\n" +
                "\n" +
                "1. Start by responding in a conversational tone that aligns naturally with the user's input, ensuring your response feels tailored and relatable.\n" +
                "2. Subtly acknowledge the user's feelings based on their input or inferred state, but avoid making feelings the central focus of the conversation.\n" +
                "3. Use open-ended questions and reflections to encourage the user to elaborate or think more deeply about their experiences, fostering a dynamic and interactive dialogue.\n" +
                "4. Actively listen by referencing details from the entire conversation string provided. Analyze the full context to understand recurring themes, past details, or inconsistencies. If the input seems unclear or contradictory, infer the most likely context to maintain coherence.\n" +
                "5. Ask thoughtful and relevant questions to encourage interaction and help the user expand on their thoughts or clarify their perspective. Balance questions with reflective statements to create a conversational flow.\n" +
                "6. Avoid giving direct advice unless explicitly requested. Instead, guide the user toward self-reflection and personal clarity.\n" +
                "7. Be warm, empathetic, and professional throughout, creating a supportive and engaging environment for discussion.\n" +
                "8. If the conversation involves any mention of a medication, provide relevant information about its common side effects, precautions, and the recommended timing or conditions for taking it, ensuring the response remains conversational and clear.\n" +
                "\n" +
                "Always craft responses to feel engaging, interactive, and natural while ensuring the user feels understood and encouraged to share more. For every interaction, return a single string that represents your response, directly addressing the user’s latest input and building on the context of the full conversation string. Use questions and interactions as necessary to sustain a meaningful dialogue.\n" +
                "\n" +
                "Responses should never include prefixes like 'ChatGPT:' or similar. The response must flow naturally and directly engage with the user's input as part of the conversation.";


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
