package ru.admeya.spring.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long commentId;

    @EqualsAndHashCode.Exclude //to avoid LazyInitializationException
    @ToString.Exclude //to avoid LazyInitializationException
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "description")
    private String description;

    public Comment() {
    }

    public Comment(String description) {
        this.description = description;
    }

}
