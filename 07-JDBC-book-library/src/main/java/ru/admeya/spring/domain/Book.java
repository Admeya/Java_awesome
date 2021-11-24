package ru.admeya.spring.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "books")
@NamedEntityGraph(name = "book-graph-entity", attributeNodes = {
        @NamedAttributeNode("authors"),
        @NamedAttributeNode("genres")
})
public class Book {

    @Id
    @Column(name = "book_id")
    private long bookId;

    @Column(name = "name")
    private String name;

    @OneToMany(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Set<Author> authors;

    @OneToMany(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Set<Genre> genres;

    @ManyToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // Задает таблицу связей между таблицами для хранения родительской и связанной сущностью
    @JoinTable(name = "books_comments", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id"))
    private Set<Comment> comments;

    public Book(long bookId, Set<Author> authors, Set<Genre> genres, String name, Set<Comment> comments) {
        this.bookId = bookId;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public Book(Set<Author> authors, Set<Genre> genres, String name, Set<Comment> comments) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public Book() {

    }
}
