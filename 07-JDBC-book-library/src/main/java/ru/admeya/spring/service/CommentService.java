package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.jpa.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepositoryJpa;
    private final BookService bookService;

    public CommentService(CommentRepository commentRepository, BookService bookService) {
        this.commentRepositoryJpa = commentRepository;
        this.bookService = bookService;

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

    public Set<Comment> getCommentByBookId(long bookId) {
        Book book = bookService.getBookById(bookId);
        return book.getComments();
    }
}
