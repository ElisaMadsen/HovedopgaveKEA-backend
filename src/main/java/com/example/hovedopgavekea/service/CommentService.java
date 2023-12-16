package com.example.hovedopgavekea.service;

import com.example.hovedopgavekea.model.Comment;
import com.example.hovedopgavekea.model.FieldOfStudy;
import com.example.hovedopgavekea.repository.CommentRepository;
import com.example.hovedopgavekea.repository.FieldOfStudyRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CommentService implements ICommentService{

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Override
    public Set<Comment> findAll() {
        Set<Comment> comments = new HashSet<>();
        commentRepository.findAll().forEach(comments::add);
        return comments;
    }

    @Override
    public Comment save(Comment object) {
        commentRepository.save(object);
        return object;
    }

    @Override
    public void delete(Comment object) {
        commentRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        commentRepository.deleteById(aLong);
    }

    @Override
    public Optional<Comment> findById(Long aLong) {
        return commentRepository.findById(aLong);
    }
}
