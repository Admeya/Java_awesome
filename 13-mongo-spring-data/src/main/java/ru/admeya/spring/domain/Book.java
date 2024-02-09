package ru.admeya.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "books")
public class Book {

    @Id
    private String bookId;

    private String name;

    private String genreId;

    @DBRef
    private List<Author> authors = new ArrayList<>();

    public Book(String bookId, String genreId, String name) {
        this.bookId = bookId;
        this.name = name;
        this.genreId = genreId;
    }

    public Book(String genreId, String name) {
        this.name = name;
        this.genreId = genreId;
    }

    public Book() {

    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", name='" + name + '\'' +
                ", authors=" + authors +
                ", genreId =" + genreId +
                '}';
    }
}
