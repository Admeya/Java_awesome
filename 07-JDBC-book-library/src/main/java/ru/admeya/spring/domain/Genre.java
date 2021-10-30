package ru.admeya.spring.domain;

import lombok.Data;

@Data
public class Genre {

    private long genreId;

    private String name;

    public Genre(long genreId, String name) {
        this.genreId = genreId;
        this.name = name;
    }

    public Genre(String name) {
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
