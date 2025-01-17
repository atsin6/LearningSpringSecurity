package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories;


import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
