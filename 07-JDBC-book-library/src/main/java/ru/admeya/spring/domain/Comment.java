package ru.admeya.spring.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="comments")
public class Comment {

    public Comment() {
    }

    @Id
    @Column(name="comment_id")
    private long commentId;

    @Column(name="description")
    private String description;

    public Comment(long commentId, String description) {
        this.commentId = commentId;
        this.description = description;
    }

    public Comment(String description) {
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
