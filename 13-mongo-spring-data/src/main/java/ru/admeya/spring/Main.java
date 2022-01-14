package ru.admeya.spring;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.repository.AuthorRepository;
import ru.admeya.spring.repository.BookRepository;
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

        GenreService genreService = new GenreServiceImpl(genreRepository);
        AuthorService authorService = new AuthorServiceImpl(authorRepository);
        BookService bookService = new BookServiceImpl(authorService, bookRepository, genreService);


        List<Book> books = bookService.getAllBooks();
        System.out.println(books);

        System.out.println(bookService.getBookById(books.get(0).getBookId()));

        System.out.println(authorService.getAllAuthors());
        System.out.println(genreService.getAllGenres());


        System.out.println(bookService.insertBook("Hans", "Cristian", "Andersen",
                "Travelling to the center of the Earth", "story", "comment"));

        System.out.println(authorService.getAllAuthors());
        System.out.println(genreService.getAllGenres());
        System.out.println(bookService.getAllBooks());

        System.out.println(bookService.insertBook("Hans", "Cristian", "Andersen",
                "Thumbelina", "story", "very good story"));

        System.out.println(authorService.getAllAuthors());
        System.out.println(genreService.getAllGenres());
        System.out.println(bookService.getAllBooks());

    }
}
