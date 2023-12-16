package com.example.hovedopgavekea.repository;

import com.example.hovedopgavekea.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
