package ru.admeya.spring.jpa;

import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Genre save(Genre genre);

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String genreName);

    List<Genre> findAll();

    void deleteById(long id);
}
