package ru.admeya.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.admeya.spring.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DAO authors should be")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final String EXISTING_AUTHOR_NAME = "John";
    private static final String EXISTING_AUTHOR_MIDDLENAME = "Valter";
    private static final String EXISTING_AUTHOR_SURNAME = "Scott";

    @Autowired
    private AuthorDaoJdbc authorDao;

    @DisplayName("Return expected authors count in DB")
    @Test
    void shouldReturnExpectedPersonCount() {
        int actualPersonsCount = authorDao.getCount();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("Insert author to DB")
    @Test
    void insert() {
        Author author = new Author(2, "Ivan", "Ivanovich", "Ivanov");
        long id = authorDao.insert(author);
        Author actualAuthor = authorDao.getAuthorById(id);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(author);
    }

    @DisplayName("Should delete author from DB")
    @Test
    void deleteById() {
        assertThatCode(() -> authorDao.getAuthorById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.getAuthorById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Should return all authors")
    @Test
    void getAllAuthors() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        List<Author> authors = authorDao.getAllAuthors();
        assertThat(authors)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }

    @DisplayName("Should get authors by id")
    @Test
    void getAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        Author actualAuthor = authorDao.getAuthorById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Should get authors by fio")
    @Test
    void getAuthorByFIO() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        Author actualAuthor = authorDao.getAuthorByFIO(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}