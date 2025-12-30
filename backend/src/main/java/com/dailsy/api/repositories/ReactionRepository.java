package com.dailsy.api.repositories;

import com.dailsy.api.models.Post;
import com.dailsy.api.models.Reaction;
import com.dailsy.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByUserAndPost(User user, Post post);
    long countByPost(Post post);
}
