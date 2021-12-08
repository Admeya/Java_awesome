package ru.admeya.spring.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "genres")
public class Genre {

    @Id
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
        return String.join(" ",
                String.valueOf(genreId),
                name,
                "\n");
    }
}
