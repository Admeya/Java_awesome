package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.GenreRepository;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
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
        Genre genre;

        Genre existGenre = genreRepository.findByName(genreName).orElse(null);
        if (existGenre == null) {
            genre = genreRepository.save(new Genre(genreName));
        } else {
            genre = existGenre;
        }
        return genre;
    }
}
