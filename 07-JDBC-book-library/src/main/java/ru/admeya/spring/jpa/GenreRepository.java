package ru.admeya.spring.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> findByName(String genreName);

    List<Genre> findAll();
}
