package ru.admeya.spring.jpa;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.admeya.spring.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA authors should be")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    private static final int EXPECTED_AUTHORS_COUNT = 1;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "John";
    private static final String EXISTING_AUTHOR_MIDDLENAME = "Valter";
    private static final String EXISTING_AUTHOR_SURNAME = "Scott";

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Return expected authors count in DB")
    @Test
    void shouldReturnExpectedPersonCount() {
        int actualPersonsCount = authorRepositoryJpa.getCount();
        assertThat(actualPersonsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("Insert author to DB")
    @Test
    void insert() {
        Author author = new Author(2, "Ivan", "Ivanovich", "Ivanov");
        Author savedAuthor = authorRepositoryJpa.save(author);
        Optional<Author> actualAuthor = authorRepositoryJpa.findById(savedAuthor.getId());
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(author);
    }

    @DisplayName("Should delete author from DB")
    @Test
    void deleteById() {

        Author firstAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(firstAuthor).isNotNull();
        em.detach(firstAuthor);

        authorRepositoryJpa.deleteById(EXISTING_AUTHOR_ID);
        Author deletedStudent = em.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(deletedStudent).isNull();
    }

    @DisplayName("Should return all authors")
    @Test
    void getAllAuthors() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        List<Author> authors = authorRepositoryJpa.findAll();
        assertThat(authors)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedAuthor);
    }

    @DisplayName("Should get authors by id")
    @Test
    void getAuthorById() {
        Author expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        Optional<Author> actualAuthor = authorRepositoryJpa.findById(expectedAuthor.getId());
        assertThat(actualAuthor.get()).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @DisplayName("Should get authors by fio")
    @Test
    void getAuthorByFIO() {
        Author expectedAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        List<Author> actualAuthorList = authorRepositoryJpa.findByFIO(EXISTING_AUTHOR_NAME, EXISTING_AUTHOR_MIDDLENAME, EXISTING_AUTHOR_SURNAME);
        assertThat(actualAuthorList).containsOnlyOnce(expectedAuthor);
    }
}