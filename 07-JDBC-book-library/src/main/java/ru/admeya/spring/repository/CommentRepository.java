package ru.admeya.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {

    @Override
    List<Comment> findAll();
}
