package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.CommentRepositoryJpa;
import ru.admeya.spring.jpa.GenreRepositoryJpa;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepositoryJpa genreRepository;

    public GenreService(GenreRepositoryJpa genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre insertGenre(String genre) {
        return genreRepository.save(new Genre(genre));
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(long id) {
        return genreRepository.findById(id).get();
    }

    public void delGenreById(long id) {
        genreRepository.deleteById(id);
    }

    public Genre getGenreByName(String genreName) {
        return genreRepository.findByName(genreName).get();
    }
}
