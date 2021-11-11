package ru.admeya.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.admeya.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DAO genres should be")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "sport";

    @Autowired
    private GenreDaoJdbc genreDao;

    @DisplayName("Insert genre to DB")
    @Test
    void insert() {
        Genre genre = new Genre(2, "psychology");
        long id = genreDao.insert(genre);
        Genre actualGenre = genreDao.getGenreById(id);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @DisplayName("Should delete genre from DB")
    @Test
    void deleteById() {
        assertThatCode(() -> genreDao.getGenreById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDao.deleteById(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreDao.getGenreById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Should return all genres")
    @Test
    void getAllGenres() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> genres = genreDao.getAllGenres();
        assertThat(genres)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @DisplayName("Should get genres by id")
    @Test
    void getGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getGenreById(expectedGenre.getGenreId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Should get genre by name")
    @Test
    void getGenreByName() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDao.getGenreByName(expectedGenre.getName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}