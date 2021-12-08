package ru.admeya.spring.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private long genreId;

    @Column(name = "name")
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genreId=" + genreId +
                ", name='" + name + '\'' +
                '}';
    }
}
