package ru.admeya.spring.domain;

import lombok.Data;

@Data
public class Book {

    private long bookId;

    private long authorId;

    private long genreId;

    private String name;

    private String authorName;

    private String genreName;

    public Book(long bookId, long authorId, long genreId, String name) {
        this.bookId = bookId;
        this.authorId = authorId;
        this.genreId = genreId;
        this.name = name;
    }

    public Book(long authorId, long genreId, String name) {
        this.authorId = authorId;
        this.genreId = genreId;
        this.name = name;
    }
}
