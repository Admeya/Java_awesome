package ru.admeya.spring;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.admeya.spring.repository.AuthorRepository;
import ru.admeya.spring.repository.BookRepository;
import ru.admeya.spring.repository.GenreRepository;
import ru.admeya.spring.service.*;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);
        Console.main(args);

        AuthorRepository authorRepository = context.getBean(AuthorRepository.class);
        BookRepository bookRepository = context.getBean(BookRepository.class);
        GenreRepository genreRepository = context.getBean(GenreRepository.class);

        GenreService genreService = new GenreServiceImpl(genreRepository);
        AuthorService authorService = new AuthorServiceImpl(authorRepository);
        BookService bookService = new BookServiceImpl(authorService, bookRepository, genreService);


        System.out.println(bookService.getAllBooks());

        System.out.println(bookService.getBookById(2));

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
