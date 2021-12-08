package ru.admeya.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private BookService bookService;

    @Test
    void checkingExistBooks() {

        /* Исходные ожидаемые значения*/
        Author author1 = new Author(1, "John", "Valter", "Scott");
        Author author2 = new Author(2, "Ivan", "Ivanovich", "Ivanov");
        List<Author> expectedAuthors = List.of(author1, author2);
        Genre genre = new Genre(1, "sport");
        List<Comment> comments1 = List.of(
                new Comment(1, 1, "Very good book"),
                new Comment(2, 1, "Not so bad"));
        Book expectedBook1 = new Book(1, expectedAuthors, genre, "Travelling", comments1);
        Book expectedBook2 = new Book(2, List.of(author1), genre, "Alyaska", List.of());
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2);
        List<Genre> expectedGenres = List.of(genre);
        /*--------------------------------------------------------------------------------------------*/
        /*Значения после вставки новой книги*/
        Author author3 = new Author(2, "Hans", "Cristian", "Andersen");
        List<Comment> comments3 = List.of(new Comment(3, 3, "comment"));
        Genre genre3 = new Genre(2, "story");
        Book expectedBook3 = new Book(3, List.of(author3), genre, "Travelling to the center of the Earth", comments3);
        /*--------------------------------------------------------------------------------------------*/


        /* Реальные значения из бд: authors, books, genres*/
        List<Book> allBooks = bookService.getAllBooks();
        List<Author> allAuthors = authorService.getAllAuthors();
        List<Genre> allGenres = genreService.getAllGenres();
        /**/


        // Исходные данные - в БД 2 книги, проверяем что это ожидаемые
        assertThat(allBooks)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedBooks);

        // Исходные данные - в таблице авторов - 2 автора
        assertThat(allAuthors)
                .hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedAuthors);

        // Исходные данные - в таблице жанров - 1 жанр
        assertThat(allGenres)
                .hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedGenres);
    }
}