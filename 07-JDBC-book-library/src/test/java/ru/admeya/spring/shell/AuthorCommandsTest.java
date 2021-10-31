package ru.admeya.spring.shell;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.Shell;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.admeya.spring.dao.AuthorDaoJdbc;
import ru.admeya.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Shell commands for authors should")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class AuthorCommandsTest {

    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "ivan";
    private static final String EXISTING_AUTHOR_MIDDLENAME = "ivanovich";
    private static final String EXISTING_AUTHOR_SURNAME = "ivanov";

    @MockBean
    private AuthorDaoJdbc authorDao;

    @Autowired
    private Shell shell;

    @DisplayName("Return authors count")
    @Test
    void countAuthors() {
        final String commandCount = "count-authors";

        given(authorDao.getCount()).willReturn(5);
        int res = (Integer) shell.evaluate(() -> commandCount);

        assertThat(res).isEqualTo(5);
    }

    @DisplayName("Insert author and return count row")
    @Test
    void insertAuthor() {
        Long expectedInsertedRow = 1L;
        final String commandInsert = "insert-author " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME;

        String expectedResult = String.join(" ",
                "author add:",
                EXISTING_AUTHOR_NAME,
                EXISTING_AUTHOR_MIDDLENAME,
                EXISTING_AUTHOR_SURNAME,
                "with id:",
                String.valueOf(expectedInsertedRow));

        given(authorDao.insert(new Author(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME)))
                .willReturn(expectedInsertedRow);

        String res = (String) shell.evaluate(() -> commandInsert);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return all authors")
    @Test
    void getAllAuthors() {

        final String commandGetAuthors = "get-all-authors";

        List<Author> authors = List.of(
                new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME),
                new Author(2, "petr", "petrovich", "petrov")
        );

        given(authorDao.getAllAuthors()).willReturn(authors);

        String expectedResult =
                EXISTING_AUTHOR_ID + " " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME + " \n" +
                        "2 petr petrovich petrov \n";

        String res = (String) shell.evaluate(() -> commandGetAuthors);

        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return author by Id")
    @Test
    void getAuthorById() {

        final String commandGetAuthorById = "get-author-by-id 1";

        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        String expectedResult = EXISTING_AUTHOR_ID + " " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME + " \n";

        given(authorDao.getAuthorById(EXISTING_AUTHOR_ID)).willReturn(expectedAuthor);

        String res = (String) shell.evaluate(() -> commandGetAuthorById);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Delete author by id")
    @Test
    void delAuthorById() {
        final String commandDelAuthorById = "del-author-by-id 1";
        String expectedResult = "author deleted";

        String res = (String) shell.evaluate(() -> commandDelAuthorById);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return author by fio")
    @Test
    void getAuthorByFIO() {
        final String commandGetAuthorByFIO = "get-author-by-fio " + EXISTING_AUTHOR_NAME + " " +
                EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME;

        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        String expectedResult = EXISTING_AUTHOR_ID + " " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME + " \n";

        given(authorDao.getAuthorByFIO(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME))
                .willReturn(expectedAuthor);

        String res = (String) shell.evaluate(() -> commandGetAuthorByFIO);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return info about not found author")
    @Test
    void getAuthorByFIONotFound() {
        final String commandGetAuthorByFio = "get-author-by-fio " + "petr" + " " +
                EXISTING_AUTHOR_MIDDLENAME + " " + EXISTING_AUTHOR_SURNAME;

        String expectedResult = "Author not found";

        given(authorDao.getAuthorByFIO("petr", EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME))
                .willReturn(null);

        String res = (String) shell.evaluate(() -> commandGetAuthorByFio);
        assertThat(res).isEqualTo(expectedResult);
    }
}