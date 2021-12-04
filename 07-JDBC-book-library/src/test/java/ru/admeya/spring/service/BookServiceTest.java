package ru.admeya.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.BookRepository;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void getBookByAuthorId() {
        when(authorService.getAuthorById(1L)).thenReturn(new Author(1, "John", "Valter", "Scott"));

        List<Author> authors1 = List.of(new Author(1, "John", "Valter", "Scott"),
                new Author(2, "Ivan", "Ivanovich", "Ivanov"));
        List<Genre> genres1 = List.of(new Genre(1, "sport"));
        List<Comment> comments1 = List.of(new Comment(1, 1, "Very good book"), new Comment(2, 1, "Not so bad"));
        Book expectedBook1 = new Book(1, authors1, genres1, "Travelling", comments1);
        Book expectedBook2 = new Book(
                2,
                List.of(new Author(1, "John", "Valter", "Scott")),
                List.of(),
                "Alyaska",
                List.of());
        List<Book> expectedBooks = List.of(expectedBook1, expectedBook2);
        when(bookRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getBooksByAuthorId(1L);

        assertThat(expectedBooks).usingRecursiveComparison().isEqualTo(actualBooks);
    }
}