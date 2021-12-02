package ru.admeya.spring.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Override
    List<Comment> findAll();
}
