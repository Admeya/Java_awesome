package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.jpa.CommentRepositoryJpa;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepositoryJpa commentRepositoryJpa;

    public CommentService(CommentRepositoryJpa commentRepositoryJpa) {
        this.commentRepositoryJpa = commentRepositoryJpa;
    }

    @Transactional
    public Comment saveComment(String comment) {
        return commentRepositoryJpa.save(new Comment(comment));
    }

    @Transactional
    public List<Comment> getAllComments() {
        return commentRepositoryJpa.findAll();
    }

    @Transactional
    public Comment getCommentById(long id) {
        return commentRepositoryJpa.findById(id).get();
    }

    @Transactional
    public void delCommentById(long id) {
        commentRepositoryJpa.deleteById(id);
    }
}
