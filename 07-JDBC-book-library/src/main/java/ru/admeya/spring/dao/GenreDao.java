package ru.admeya.spring.dao;

import ru.admeya.spring.domain.Genre;

import java.util.List;

public interface GenreDao {

    long insert(Genre genre);

    void deleteById(long id);

    List<Genre> getAllGenres();

    Genre getGenreById(long id);

    Genre getGenreByName(String genreName);
}
