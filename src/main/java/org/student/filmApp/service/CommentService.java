package org.student.filmApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.student.filmApp.domain.Comment;
import org.student.filmApp.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Transactional
    public void save(Comment comment) {
        comment.setDateOfPublish(LocalDateTime.now());
        Comment c = commentRepository.save(comment);
        System.out.println("XXXXXX " + c.getId());
    }

    @Transactional
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
