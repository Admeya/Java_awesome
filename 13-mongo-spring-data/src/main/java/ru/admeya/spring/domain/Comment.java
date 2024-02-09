package ru.admeya.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "comments")
public class Comment {

    @Id
    private String commentId;

    private String bookId;

    private String description;

    public Comment() {
    }

    public Comment(String description) {
        this.description = description;
    }

    public Comment(String commentId, String bookId, String description) {
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
