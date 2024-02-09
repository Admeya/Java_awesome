package ru.admeya.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.AuthorRepository;
import ru.admeya.spring.repository.BookRepository;
import ru.admeya.spring.repository.CommentRepository;
import ru.admeya.spring.repository.GenreRepository;
import ru.admeya.spring.service.*;

import java.util.List;

@EnableMongock
@EnableMongoRepositories
@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);
        CommentRepository commentRepository = context.getBean(CommentRepository.class);

        GenreService genreService = new GenreServiceImpl(genreRepository);
        AuthorService authorService = new AuthorServiceImpl(authorRepository);
        BookService bookService = new BookServiceImpl(authorService, bookRepository, genreService);
        CommentService commentService = new CommentServiceImpl(commentRepository, bookService);


        List<Book> books = bookService.getAllBooks();
        System.out.println(books);

        System.out.println(bookService.getBookById(books.get(0).getBookId()));

        System.out.println(authorService.getAllAuthors());
        List<Genre> genres = genreService.getAllGenres();
        System.out.println(genres);


        System.out.println(bookService.insertBook("Hans", "Cristian", "Andersen",
                "Travelling to the center of the Earth", "story", "comment"));

        Author author1 = new Author("Rikki", "Martin", "Oreiro");
        Author author2 = new Author("Natalia", "Maskabara", "Oreiro");
        Genre genre = genres.get(0);
        Book book = new Book(genre.getGenreId(), "What do you thing about the world");

        List<Author> savedAuthors = authorRepository.saveAll(List.of(author1, author2));
        book.setAuthors(savedAuthors);

        Book savedBookTwoAuthors = bookService.insertBook(book);
        System.out.println("I can save two authors in 1 book: " + savedBookTwoAuthors);

        System.out.println(authorService.getAllAuthors());
        System.out.println(genreService.getAllGenres());
        System.out.println(bookService.getAllBooks());

        System.out.println("Insert the same author");
        System.out.println(bookService.insertBook("Hans", "Cristian", "Andersen",
                "Thumbelina", "story", "very good story"));

        System.out.println(authorService.getAllAuthors());
        System.out.println(genreService.getAllGenres());
        System.out.println(bookService.getAllBooks());

        List<Comment> commentsBook1 = commentService.getCommentByBookId(books.get(0).getBookId());
        System.out.println(commentsBook1.get(0).getDescription());
        System.out.println(commentsBook1.get(1).getDescription());

    }
}
