package ru.admeya.spring.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentId;

    @Column(name = "book_id")
    private long bookId;

    @Column(name = "description")
    private String description;

    public Comment() {
    }

    public Comment(String description) {
        this.description = description;
    }

    public Comment(long commentId, long bookId, String description) {
        this.bookId = bookId;
        this.commentId = commentId;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", bookId=" + bookId +
                ", description='" + description + '\'' +
                '}';
    }
}
