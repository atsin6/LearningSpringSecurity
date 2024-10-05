package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    SessionEntity findByUserId(Long userId);
//    SessionEntity findByUserIdAndActiveTrue(Long userId);
    SessionEntity findBySessionToken(String jwtToken);
}