package ru.admeya.spring.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long bookId;

    @Column(name = "name")
    private String name;

    @ManyToMany(targetEntity = Author.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "books_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors;

    @ManyToOne(targetEntity = Genre.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @OneToMany(targetEntity = Comment.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @BatchSize(size = 5)
    private List<Comment> comments;

    public Book(long bookId, List<Author> authors, Genre genre, String name, List<Comment> comments) {
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
