package ru.admeya.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Getter
@Setter
@Document(collection = "books")
public class Book {

    @Id
    private String bookId;

    private String name;

    @DBRef
    private List<Author> authors;

    @DBRef
    private Genre genre;

    @DBRef
    private List<Comment> comments;

    public Book(String bookId, List<Author> authors, Genre genre, String name, List<Comment> comments) {
        this.bookId = bookId;
        this.name = name;
        this.authors = authors;
        this.genre = genre;
        this.comments = comments;
    }

    public Book(List<Author> authors, Genre genre, String name, List<Comment> comments) {
        this.name = name;
        this.authors = authors;
        this.genre = genre;
        this.comments = comments;
    }

    public Book() {

    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", genre=" + genre +
                ", comments=" + comments +
                '}';
    }
}
