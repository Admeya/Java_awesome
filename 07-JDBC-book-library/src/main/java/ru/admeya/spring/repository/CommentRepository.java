package ru.admeya.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllCommentsByBook(Book book);

}
