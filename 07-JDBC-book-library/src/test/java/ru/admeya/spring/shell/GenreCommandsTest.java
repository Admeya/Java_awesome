package ru.admeya.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.admeya.spring.dao.GenreDaoJdbc;
import ru.admeya.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Shell commands for genres should")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class GenreCommandsTest {

    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "sport";

    @MockBean
    private GenreDaoJdbc genreDao;

    @Autowired
    private Shell shell;

    @DisplayName("Insert genre and return count row")
    @Test
    void insertGenre() {
        Long expectedInsertedRow = 1L;
        final String commandInsert = "insert-genre " + EXISTING_GENRE_NAME;

        String expectedResult = String.join(" ",
                "genre add:",
                EXISTING_GENRE_NAME,
                "with id:",
                String.valueOf(EXISTING_GENRE_ID));

        given(genreDao.insert(new Genre(EXISTING_GENRE_NAME)))
                .willReturn(expectedInsertedRow);

        String res = (String) shell.evaluate(() -> commandInsert);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return all genres")
    @Test
    void getAllGenres() {
        final String commandGetGenres = "get-all-genres";

        List<Genre> genres = List.of(
                new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME),
                new Genre(2, "moda")
        );

        given(genreDao.getAllGenres()).willReturn(genres);

        String expectedResult =
                EXISTING_GENRE_ID + " " + EXISTING_GENRE_NAME + " \n" +
                        "2 moda \n";

        String res = (String) shell.evaluate(() -> commandGetGenres);

        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return genre by Id")
    @Test
    void getGenreById() {
        final String commandGetGenreById = "get-genre-by-id " + EXISTING_GENRE_ID;

        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        String expectedResult = EXISTING_GENRE_ID + " " + EXISTING_GENRE_NAME + " \n";

        given(genreDao.getGenreById(EXISTING_GENRE_ID))
                .willReturn(expectedGenre);

        String res = (String) shell.evaluate(() -> commandGetGenreById);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Delete genre by id")
    @Test
    void delGenreById() {
        final String commandDelGenreById = "del-genre-by-id 1";
        String expectedResult = "genre deleted";

        String res = (String) shell.evaluate(() -> commandDelGenreById);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return genre by name")
    @Test
    void getGenreByName() {
        final String commandGetGenreByName = "get-genre-by-name " + EXISTING_GENRE_NAME;

        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        String expectedResult = EXISTING_GENRE_ID + " " + EXISTING_GENRE_NAME + " \n";

        given(genreDao.getGenreByName(EXISTING_GENRE_NAME))
                .willReturn(expectedGenre);

        String res = (String) shell.evaluate(() -> commandGetGenreByName);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return info about not found genre")
    @Test
    void getGenreByNameNotFound() {
        String nonExpectedGenre = "cooking";
        final String commandGetGenreByName = "get-genre-by-name " + nonExpectedGenre;

        String expectedResult = "Genre not found";

        given(genreDao.getGenreByName(nonExpectedGenre))
                .willReturn(null);

        String res = (String) shell.evaluate(() -> commandGetGenreByName);
        assertThat(res).isEqualTo(expectedResult);
    }
}