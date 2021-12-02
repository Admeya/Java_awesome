package ru.admeya.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    private long bookId;

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.ALL)
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Fetch(FetchMode.SUBSELECT)
    private Set<Author> authors;

    @OneToMany(targetEntity = Genre.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "genre_id")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Genre> genres;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @BatchSize(size = 5)
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
