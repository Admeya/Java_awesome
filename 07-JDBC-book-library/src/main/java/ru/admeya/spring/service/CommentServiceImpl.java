package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Comment saveComment(String comment) {
        return commentRepositoryJpa.save(new Comment(comment));
    }

    @Transactional(readOnly = true)
    public List<Comment> getAllComments() {
        return commentRepositoryJpa.findAll();
    }

    @Transactional(readOnly = true)
    public Comment getCommentById(long id) {
        return commentRepositoryJpa.findById(id).get();
    }

    @Transactional
    public void delCommentById(long id) {
        commentRepositoryJpa.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentByBookId(long bookId) {
        Book book = bookService.getBookById(bookId);
        return book.getComments();
    }
}
