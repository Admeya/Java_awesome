package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final GenreService genreService;

    public BookServiceImpl(AuthorService authorService, BookRepository bookRepository, GenreService genreService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.genreService = genreService;
    }

    public Book insertBook(
            String name,
            String middleName,
            String surname,
            String bookName,
            String genreName,
            String comment) {

        Author author = authorService.getAuthorByFIO(name, middleName, surname);
        Genre genre = genreService.getGenreByName(genreName);
        Book book = new Book(genre.getGenreId(), bookName);
        book.setAuthors(List.of(author));
        return bookRepository.save(book);
    }

    public Book insertBook (Book book) {
        return bookRepository.save(book);
    };

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void delBookById(String id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(String id) {
        return bookRepository.findById(id).get();
    }

}
