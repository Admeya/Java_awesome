package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.jpa.*;

import java.util.List;
import java.util.Set;

@Service
public class BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepositoryJpa genreRepository;
    private final CommentRepositoryJpa commentRepository;

    public BookService(AuthorRepositoryJpa authorRepository, BookRepositoryJpa bookRepository, GenreRepositoryJpa genreRepository, CommentRepositoryJpa commentRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.commentRepository = commentRepository;
    }

    public Book insertBook(
            String name,
            String middleName,
            String surname,
            String bookName,
            String genreName,
            String comment) {

        Author author = findAuthor(name, middleName, surname);
        Genre genre = findGenre(genreName);
        Set<Comment> comments = Set.of(new Comment(comment));

        return bookRepository.save(new Book(Set.of(author), Set.of(genre), bookName, comments));
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public void delBookById(long id) {
        bookRepository.deleteById(id);
    }

    public Book getBookById(long id) {
        return bookRepository.findById(id).get();
    }

    private Author findAuthor(String name, String middleName, String surname) {
        Author author;
        List<Author> authors = authorRepository.findByFIO(name, middleName, surname);
        if (authors.isEmpty()) {
            author = new Author(name, middleName, surname);
        } else {
            author = authors.get(0);
        }
        return author;
    }

    private Genre findGenre(String genreName) {
        Genre genre;

        Genre existGenre = genreRepository.findByName(genreName).get();
        if (existGenre == null) {
            genre = new Genre(genreName);
        } else {
            genre = existGenre;
        }
        return genre;
    }
}
