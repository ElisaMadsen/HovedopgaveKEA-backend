package com.example.hovedopgavekea.repository;

import com.example.hovedopgavekea.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
