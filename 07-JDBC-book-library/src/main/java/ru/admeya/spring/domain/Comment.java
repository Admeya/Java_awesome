package ru.admeya.spring.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Data
@Entity
@Table(name = "comments")
public class Comment {

    @Id
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

    public Comment(long commentId, String description) {
        this.commentId = commentId;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.join(" ",
                String.valueOf(commentId),
                description,
                "\n");
    }
}
