package ru.admeya.spring.service;

import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(String comment);

    List<Comment> getAllComments();

    Comment getCommentById(String id);

    void delCommentById(String id);

    List<Comment> getCommentByBookId(String bookId);
}
