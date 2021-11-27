package ru.admeya.spring.jpa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA books should be")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final Long EXISTING_BOOK_ID = 1L;
    private static final Long EXISTING_AUTHOR_ID = 1L;
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
        Set<Comment> comments = Set.of(new Comment("Very good book"));

        Book book = new Book(2, authors, genres, "How to loose friends", comments);
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
        Set<Author> authors = Set.of(
                new Author(1, "John", "Valter", "Scott"),
                new Author(2, "Ivan", "Ivanovich", "Ivanov"));
        Set<Genre> genres = Set.of(new Genre(1, "sport"));
        Set<Comment> comments = Set.of(
                new Comment(1, "Very good book"),
                new Comment(2, "Not so bad"));
        Book expectedBook = new Book(EXISTING_BOOK_ID, authors, genres, EXISTING_BOOK_NAME, comments);
        List<Book> books = bookRepositoryJpa.getAllBooks();
        assertThat(books)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("Should get books by id")
    @Test
    void getBookById() {
        Set<Author> authors = Set.of(new Author(1, "John", "Valter", "Scott"),
                new Author(2, "Ivan", "Ivanovich", "Ivanov"));
        Set<Genre> genres = Set.of(new Genre(1, "sport"));
        Set<Comment> comments = Set.of(new Comment(1, "Very good book"), new Comment(2, "Not so bad"));
        Book expectedBook = new Book(EXISTING_BOOK_ID, authors, genres, EXISTING_BOOK_NAME, comments);
        Book actualBook = bookRepositoryJpa.findById(expectedBook.getBookId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Should get comments by book id")
    @Test
    void getCommentByBookId() {
        Set<String> expectedComments = Set.of("Very good book", "Not so bad");
        Book book = bookRepositoryJpa.findById(EXISTING_BOOK_ID).get();
        Set<Comment> actualComments = book.getComments();
        assertThat(actualComments)
                .hasSize(2)
                .extracting(comment -> comment.getDescription())
                .containsExactlyInAnyOrderElementsOf(expectedComments);
    }
}