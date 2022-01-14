package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.repository.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepositoryJpa;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepositoryJpa = commentRepository;
        this.bookService = bookService;

    }

    public Comment saveComment(String comment) {
        return commentRepositoryJpa.save(new Comment(comment));
    }

    public List<Comment> getAllComments() {
        return commentRepositoryJpa.findAll();
    }

    public Comment getCommentById(String id) {
        return commentRepositoryJpa.findById(id).get();
    }

    public void delCommentById(String id) {
        commentRepositoryJpa.deleteById(id);
    }

    public List<Comment> getCommentByBook(Book book) {
        List<Comment> comments = commentRepositoryJpa.findAllCommentsByBook(book);

        return comments;
    }
}
