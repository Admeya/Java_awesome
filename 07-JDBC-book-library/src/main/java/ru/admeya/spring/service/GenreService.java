package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.GenreRepositoryJpa;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GenreService {

    private final GenreRepositoryJpa genreRepository;

    public GenreService(GenreRepositoryJpa genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Genre insertGenre(String genre) {
        return genreRepository.save(new Genre(genre));
    }

    @Transactional
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Transactional
    public Genre getGenreById(long id) {
        return genreRepository.findById(id).get();
    }

    @Transactional
    public void delGenreById(long id) {
        genreRepository.deleteById(id);
    }

    @Transactional
    public Genre getGenreByName(String genreName) {
        return genreRepository.findByName(genreName).get();
    }
}
