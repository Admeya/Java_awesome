package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.repository.CommentRepository;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final BookService bookService;

    public CommentServiceImpl(CommentRepository commentRepository, BookService bookService) {
        this.commentRepository = commentRepository;
        this.bookService = bookService;

    }

    public Comment saveComment(String comment) {
        return commentRepository.save(new Comment(comment));
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(String id) {
        return commentRepository.findById(id).get();
    }

    public void delCommentById(String id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<Comment> getCommentByBookId(String bookId) {
        return commentRepository.findAllCommentsByBookId(bookId);
    }

    public List<Comment> getCommentByBook(Book book) {
        return commentRepository.findAllCommentsByBookId(book.getBookId());
    }
}
