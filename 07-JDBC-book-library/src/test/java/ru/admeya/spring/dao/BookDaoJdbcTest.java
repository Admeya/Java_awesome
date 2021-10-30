package ru.admeya.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.admeya.spring.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DAO books should be")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    private static final int EXISTING_BOOK_ID = 1;
    private static final int EXISTING_AUTHOR_ID = 1;
    private static final int EXISTING_GENRE_ID = 1;
    private static final String EXISTING_BOOK_NAME = "Travelling";

    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("Insert book to DB")
    @Test
    void insert() {
        Book book = new Book(2, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, "How to loose friends");
        bookDao.insert(book);
        Book actualBook = bookDao.getBookById(2);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Should delete book from DB")
    @Test
    void deleteById() {
        assertThatCode(() -> bookDao.getBookById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> bookDao.getBookById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("Should return all books")
    @Test
    void getAllBooks() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_NAME);
        List<Book> books = bookDao.getAllBooks();
        assertThat(books)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedBook);
    }

    @DisplayName("Should get books by id")
    @Test
    void getBookById() {
        Book expectedBook = new Book(EXISTING_BOOK_ID, EXISTING_AUTHOR_ID, EXISTING_GENRE_ID, EXISTING_BOOK_NAME);
        Book actualBook = bookDao.getBookById(expectedBook.getBookId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}