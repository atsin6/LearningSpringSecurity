package com.atulpal.LearningSpringSecurity.LearningSpringSecurity.repositories;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<PostEntity, Long> {
}
