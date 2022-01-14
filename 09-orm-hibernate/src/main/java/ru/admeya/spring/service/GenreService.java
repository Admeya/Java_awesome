package ru.admeya.spring.service;

import ru.admeya.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre insertGenre(String genre);

    List<Genre> getAllGenres();

    Genre getGenreById(String id);

    void delGenreById(String id);

    Genre getGenreByName(String genreName);
}
