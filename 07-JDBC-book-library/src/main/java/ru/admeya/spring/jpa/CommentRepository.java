package ru.admeya.spring.jpa;

import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    List<Comment> findAll();

    void deleteById(long id);
}
