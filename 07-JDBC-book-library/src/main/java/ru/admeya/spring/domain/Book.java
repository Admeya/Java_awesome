package ru.admeya.spring.domain;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name="books")
@NamedEntityGraph(name = "book-graph-entity", attributeNodes = {
        @NamedAttributeNode("authors"),
        @NamedAttributeNode("genres")
})
public class Book {

    @Id
    @Column(name="book_id")
    private long bookId;

    @Column(name="name")
    private String name;

    @OneToMany(targetEntity = Author.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private Set<Author> authors;

    @OneToMany(targetEntity = Genre.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Set<Genre> genres;

    public Book(long bookId, Set<Author> authors, Set<Genre> genres, String name) {
        this.bookId = bookId;
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book(Set<Author> authors, Set<Genre> genres, String name) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public Book() {

    }
}
