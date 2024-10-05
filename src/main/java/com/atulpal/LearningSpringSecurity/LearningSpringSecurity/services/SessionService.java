package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.SessionEntity;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
//    private final SessionEntity session;

//    public void saveSessionByUserIdAndToken(Long userId, String token) {
//        SessionEntity sessionEntity = SessionEntity
//                .builder()
//                .userId(userId)
//                .token(token)
//                .build();
//
//        sessionRepository.save(sessionEntity);
//    }

    public void createSession(Long userId, String sessionToken) {
        //checking if an active session exists
        SessionEntity existingSession = sessionRepository.findByUserId(userId);
        if (existingSession != null && existingSession.isActive()) {
            throw new RuntimeException("Session already exists");
        }

        if(existingSession != null) {
            existingSession.setActive(false);
            sessionRepository.save(existingSession);
        }

        // Create a new session
        SessionEntity newSession = SessionEntity.builder()
                .userId(userId)
                .sessionToken(sessionToken)
                .isActive(true)
                .build();

        sessionRepository.save(newSession);
    }

    public void deactivateSession(String sessionToken) {
        SessionEntity session = sessionRepository.findBySessionToken(sessionToken);
        if (session.isActive()) {
            session.setActive(false);
            sessionRepository.save(session);
        }else{
            throw new RuntimeException("Session not found or already deactivated");
        }
    }

    public SessionEntity getSessionByUserId(Long userId) {
        return sessionRepository.findByUserId(userId);
    }

    public SessionEntity getSessionBySessionToken(String sessionToken) {
        return sessionRepository.findBySessionToken(sessionToken);
    }


    public Boolean isUserSessionActive(Long userId) {
        SessionEntity sessionEntity = sessionRepository.findByUserId(userId);
        return sessionEntity.isActive();
    }

    public Boolean isSessionExistById(Long userId) {
        return sessionRepository.existsById(userId);
    }

    public void setSessionActive(Long userId) {
        SessionEntity sessionEntity = getSessionByUserId(userId);
        sessionEntity.setActive(true);
        sessionRepository.save(sessionEntity);
    }
}
