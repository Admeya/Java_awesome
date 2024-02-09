package ru.admeya.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {

    List<Comment> findAllCommentsByBookId(String bookId);
}
