package ru.admeya.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.admeya.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA genres should be")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final Long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_NAME = "sport";

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Insert genre to DB")
    @Test
    void insert() {
        Genre genre = new Genre(2, "psychology");
        Genre savedGenre = genreRepositoryJpa.save(genre);
        Genre actualGenre = genreRepositoryJpa.findById(savedGenre.getGenreId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(genre);
    }

    @DisplayName("Should delete genre from DB")
    @Test
    void deleteById() {

        Genre firstGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(firstGenre).isNotNull();
        em.detach(firstGenre);

        genreRepositoryJpa.deleteById(EXISTING_GENRE_ID);

        Genre deletedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        assertThat(deletedGenre).isNull();
    }

    @DisplayName("Should return all genres")
    @Test
    void getAllGenres() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        List<Genre> genres = genreRepositoryJpa.findAll();
        assertThat(genres)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);
    }

    @DisplayName("Should get genres by id")
    @Test
    void getGenreById() {
        Genre expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        Genre actualGenre = genreRepositoryJpa.findById(expectedGenre.getGenreId()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Should get genre by name")
    @Test
    void getGenreByName() {
        Genre expectedGenre = em.find(Genre.class, EXISTING_GENRE_ID);
        Genre actualGenre = genreRepositoryJpa.findByName(expectedGenre.getName()).get();
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}