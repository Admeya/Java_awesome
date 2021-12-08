package ru.admeya.spring.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.BookRepositoryJpa;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private BookRepositoryJpa bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void getBookByAuthorId() {
        Author author1 = new Author(1, "John", "Valter", "Scott");
        Author author2 = new Author(2, "Ivan", "Ivanovich", "Ivanov");
        when(authorService.getAuthorById(1L)).thenReturn(author1);
        List<Author> authors = List.of(author1, author2);
        List<Genre> genres1 = List.of(new Genre(1, "sport"));
        List<Comment> comments1 = List.of(new Comment(1, "Very good book"), new Comment(2, "Not so bad"));
        Book expectedBook1 = new Book(1, authors, genres1, "Travelling", comments1);
        Book expectedBook2 = new Book(2, List.of(author1), List.of(), "Alyaska", List.of());

        List<Book> actualBooks = bookService.getBooksByAuthorId(1L);

        // У Джона Уолтера (автора с номером 1L) 2 книги, первая Travelling
        assertThat(actualBooks)
                .hasSize(2)
                .filteredOn(book ->
                        book.getName().equals(expectedBook1.getName()))
                .hasSize(1);
        // У Джона Уолтера (автора с номером 1L) 2 книги, вторая - Alyaska
        assertThat(actualBooks)
                .hasSize(2)
                .filteredOn(book ->
                        book.getName().equals(expectedBook2.getName()))
                .hasSize(1);
        // Проверяем, что у первой книги (Travelling) два автора, первый из которых Джон Уолтер
        assertThat(actualBooks.get(0).getAuthors())
                .hasSize(2)
                .filteredOn(author ->
                        author.getSurname().equals(author1.getSurname()) &&
                                author.getMiddlename().equals(author1.getMiddlename()) &&
                                author.getName().equals(author1.getName()))
                .hasSize(1);
        // Проверяем, что у первой книги (Alyaska) два автора, второй из которых Иван Иванов
        assertThat(actualBooks.get(0).getAuthors())
                .hasSize(2)
                .filteredOn(author ->
                        author.getSurname().equals(author2.getSurname()) &&
                                author.getMiddlename().equals(author2.getMiddlename()) &&
                                author.getName().equals(author2.getName()))
                .hasSize(1);
        // Проверяем, что у второй книги (Alyaska) один автор, Джон Уолтер
        assertThat(actualBooks.get(1).getAuthors())
                .hasSize(1)
                .filteredOn(author ->
                        author.getSurname().equals(author1.getSurname()) &&
                                author.getMiddlename().equals(author1.getMiddlename()) &&
                                author.getName().equals(author1.getName()))
                .hasSize(1);
    }
}