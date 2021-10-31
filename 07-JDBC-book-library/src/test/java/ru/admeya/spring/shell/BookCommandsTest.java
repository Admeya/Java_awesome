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
import ru.admeya.spring.dao.BookDaoJdbc;
import ru.admeya.spring.dao.GenreDaoJdbc;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Shell commands for books should")
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
class BookCommandsTest {

    private static final int EXISTING_BOOK_ID = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_GENRE_NAME = "sport";
    private static final String EXISTING_BOOK_NAME = "Travelling";

    private static final String EXISTING_AUTHOR_NAME = "John";
    private static final String EXISTING_AUTHOR_MIDDLENAME = "Valter";
    private static final String EXISTING_AUTHOR_SURNAME = "Scott";

    @MockBean
    private AuthorDaoJdbc authorDao;
    @MockBean
    private GenreDaoJdbc genreDao;
    @MockBean
    private BookDaoJdbc bookDao;

    @Autowired
    private Shell shell;

    @DisplayName("Insert book while author and genre found")
    @Test
    void insertBook() {

        final String commandInsertBooks = "insert-book " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " "
                + EXISTING_AUTHOR_SURNAME + " " + EXISTING_BOOK_NAME + " " + EXISTING_GENRE_NAME;

        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        given(authorDao.getAuthorByFIO(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME))
                .willReturn(expectedAuthor);

        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        given(genreDao.getGenreByName(EXISTING_GENRE_NAME))
                .willReturn(expectedGenre);

        String res = (String) shell.evaluate(() -> commandInsertBooks);
        String expectedResult = "book add: " + EXISTING_BOOK_NAME;

        assertThat(res).isEqualTo(expectedResult);

    }

    @DisplayName("Insert book while author and genre not found")
    @Test
    void insertBookNewAuthorAndGenre() {
        final String commandInsertBooks = "insert-book " + EXISTING_AUTHOR_NAME + " " + EXISTING_AUTHOR_MIDDLENAME + " "
                + EXISTING_AUTHOR_SURNAME + " " + EXISTING_BOOK_NAME + " " + EXISTING_GENRE_NAME;

        given(authorDao.getAuthorByFIO(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME))
                .willReturn(null);

        given(genreDao.getGenreByName(EXISTING_GENRE_NAME))
                .willReturn(null);

        String res = (String) shell.evaluate(() -> commandInsertBooks);
        String expectedResult = "book add: " + EXISTING_BOOK_NAME;

        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Return all books")
    @Test
    void getAllBooks() {
        final String commandGetBooks = "get-all-books";

        List<Book> books = List.of(
                new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_NAME),
                new Book(2, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, "cooking")
        );

        given(authorDao.getAuthorById(EXISTING_AUTHOR_ID))
                .willReturn(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME));

        given(genreDao.getGenreById(EXISTING_GENRE_ID))
                .willReturn(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));

        given(bookDao.getAllBooks()).willReturn(books);


        String book1 = String.join(" ",
                String.valueOf(EXISTING_BOOK_ID),
                EXISTING_BOOK_NAME,
                EXISTING_AUTHOR_SURNAME,
                EXISTING_AUTHOR_NAME.substring(0, 1),
                EXISTING_AUTHOR_MIDDLENAME.substring(0, 1),
                EXISTING_GENRE_NAME,
                "\n");
        String book2 = String.join(" ",
                String.valueOf(2),
                "cooking",
                EXISTING_AUTHOR_SURNAME,
                EXISTING_AUTHOR_NAME.substring(0, 1),
                EXISTING_AUTHOR_MIDDLENAME.substring(0, 1),
                EXISTING_GENRE_NAME,
                "\n");
        String expectedResult = book1 + book2;

        String res = (String) shell.evaluate(() -> commandGetBooks);

        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Delete book by id")
    @Test
    void delBookById() {
        final String commandDelBookById = "del-book-by-id 1";
        String expectedResult = "book deleted";

        String res = (String) shell.evaluate(() -> commandDelBookById);
        assertThat(res).isEqualTo(expectedResult);
    }

    @DisplayName("Get book by id")
    @Test
    void getBookById() {
        final String commandGetBookById = "get-book-by-id " + EXISTING_BOOK_ID;

        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_NAME);
        String expectedResult = String.join(" ",
                String.valueOf(EXISTING_BOOK_ID),
                EXISTING_BOOK_NAME,
                EXISTING_AUTHOR_SURNAME,
                EXISTING_AUTHOR_NAME.substring(0, 1),
                EXISTING_AUTHOR_MIDDLENAME.substring(0, 1),
                EXISTING_GENRE_NAME,
                "\n");
        given(authorDao.getAuthorById(EXISTING_AUTHOR_ID))
                .willReturn(new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME));

        given(genreDao.getGenreById(EXISTING_GENRE_ID))
                .willReturn(new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME));
        given(bookDao.getBookById(EXISTING_BOOK_ID)).willReturn(expectedBook);

        String res = (String) shell.evaluate(() -> commandGetBookById);
        assertThat(res).isEqualTo(expectedResult);
    }
}