package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final GenreService genreService;

    public BookService(AuthorService authorService, BookRepositoryJpa bookRepository, GenreService genreService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.genreService = genreService;
    }

    @Transactional
    public Book insertBook(
            String name,
            String middleName,
            String surname,
            String bookName,
            String genreName,
            String comment) {

        Author author = authorService.getAuthorByFIO(name, middleName, surname);
        Genre genre = genreService.getGenreByName(genreName);
        Set<Comment> comments = Set.of(new Comment(comment));

        return bookRepository.save(new Book(Set.of(author), Set.of(genre), bookName, comments));
    }

    @Transactional
    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    @Transactional
    public void delBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public Book getBookById(long id) {
        return bookRepository.findById(id).get();
    }

    public List<Book> getBooksByAuthorId(long authorId) {
        Author author = authorService.getAuthorById(authorId);
        List<Book> books = bookRepository.getAllBooks().stream().filter(book -> book.getAuthors().contains(author)).collect(Collectors.toList());
       return books;
    }
}
