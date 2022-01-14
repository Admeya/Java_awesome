package ru.admeya.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Comment;
import ru.admeya.spring.repository.AuthorRepository;
import ru.admeya.spring.repository.BookRepository;
import ru.admeya.spring.repository.CommentRepository;
import ru.admeya.spring.repository.GenreRepository;
import ru.admeya.spring.service.*;

import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);
       // Console.main(args);

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

        Book book1 = bookService.getBookById(1);
        System.out.println(book1);

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

        List<Comment> commentsBook1 = commentService.getCommentByBook(book1);
        System.out.println(commentsBook1.get(0).getDescription());
        System.out.println(commentsBook1.get(1).getDescription());

    }
}
