package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.BookRepository;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final GenreService genreService;

    public BookServiceImpl(AuthorService authorService, BookRepository bookRepository, GenreService genreService) {
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
        List<Comment> comments = List.of(new Comment(comment));

        return bookRepository.save(new Book(List.of(author), genre, bookName, comments));
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional
    public void delBookById(long id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return bookRepository.findById(id).get();
    }

}
