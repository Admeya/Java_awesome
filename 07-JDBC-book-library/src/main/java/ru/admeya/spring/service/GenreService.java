package ru.admeya.spring.service;

import ru.admeya.spring.domain.Genre;

import java.util.List;

public interface GenreService {

    Genre insertGenre(String genre);

    List<Genre> getAllGenres();

    Genre getGenreById(long id);

    void delGenreById(long id);

    Genre getGenreByName(String genreName);
}
