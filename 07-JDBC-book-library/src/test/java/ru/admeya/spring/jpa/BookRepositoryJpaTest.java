package ru.admeya.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DisplayName("JPA books should be")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_BOOK_NAME = "Travelling";

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Insert book to DB")
    @Test
    void insert() {
        Set<Author> authors = Set.of(new Author(1, "John", "Valter", "Scott"));
        Set<Genre> genres = Set.of(new Genre(1, "sport"));

        Book book = new Book(2, authors, genres, "How to loose friends");
        bookRepositoryJpa.save(book);
        Book actualBook = bookRepositoryJpa.findById(2).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Should delete book from DB")
    @Test
    void deleteById() {
        Book firstBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(firstBook).isNotNull();
        em.detach(firstBook);

        bookRepositoryJpa.deleteById(EXISTING_AUTHOR_ID);
        Book deletedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @DisplayName("Should return all books")
    @Test
    void getAllBooks() {
        Set<Author> authors = Set.of(new Author(1, "John", "Valter", "Scott"));
        Set<Genre> genres = Set.of(new Genre(1, "sport"));
        Book expectedBook = new Book(EXISTING_BOOK_ID, authors, genres, EXISTING_BOOK_NAME);
        List<Book> books = bookRepositoryJpa.getAllBooks();
        assertThat(books)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("Should get books by id")
    @Test
    void getBookById() {
        Set<Author> authors = Set.of(new Author(1, "John", "Valter", "Scott"));
        Set<Genre> genres = Set.of(new Genre(1, "sport"));
        Book expectedBook = new Book(EXISTING_BOOK_ID, authors, genres, EXISTING_BOOK_NAME);
        Book actualBook = bookRepositoryJpa.findById(expectedBook.getBookId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}