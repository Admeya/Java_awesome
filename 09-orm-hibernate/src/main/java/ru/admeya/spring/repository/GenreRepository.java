package ru.admeya.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, String> {

    Optional<Genre> findByName(String genreName);

    @Override
    List<Genre> findAll();
}
