package ru.admeya.spring.service;

import ru.admeya.spring.domain.Comment;

import java.util.List;

public interface CommentService {

    Comment saveComment(String comment);

    List<Comment> getAllComments();

    Comment getCommentById(long id);

    void delCommentById(long id);

    List<Comment> getCommentByBookId(long bookId);
}
