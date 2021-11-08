package ru.admeya.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.admeya.spring.dao.*;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Genre;

import java.util.List;

@ShellComponent
public class BookCommands {

    private final AuthorDao authorDao;
    private final BookDao bookDao;
    private final GenreDaoJdbc genreDao;

    public BookCommands(AuthorDaoJdbc authorDaoJdbc, BookDaoJdbc bookDaoJdbc, GenreDaoJdbc genreDaoJdbc) {
        this.authorDao = authorDaoJdbc;
        this.bookDao = bookDaoJdbc;
        this.genreDao = genreDaoJdbc;
    }

    @ShellMethod(value = "Add book")
    public String insertBook(
            @ShellOption String name,
            @ShellOption String middleName,
            @ShellOption String surname,
            @ShellOption String bookName,
            @ShellOption String genreName) {
        long authorId;
        long genreId;

        Author author = authorDao.getAuthorByFIO(name, middleName, surname);
        if (author == null) {
            authorId = authorDao.insert(new Author(name, middleName, surname));
        } else {
            authorId = author.getId();
        }

        Genre genre = genreDao.getGenreByName(genreName);
        if (genre == null) {
            genreId = genreDao.insert(new Genre(genreName));
        } else {
            genreId = genre.getGenreId();
        }

        bookDao.insert(new Book(authorId, genreId, bookName));

        return "book add: " + bookName;
    }

    @ShellMethod(value = "Get all books")
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        List<Genre> genres = genreDao.getAllGenres();
        List<Author> authors = authorDao.getAllAuthors();
        bookDao.getAllBooks().forEach(book -> sb.append(getBook(book, genres, authors)));
        return sb.toString();
    }

    private String getBook(Book book, List<Genre> genres, List<Author> authors) {
        long authorId = book.getAuthorId();
        long genreId = book.getGenreId();

        Author author = authors.stream().filter(author1-> author1.getId()==authorId).findFirst().get();
        Genre genre = genres.stream().filter(genre1 -> genre1.getGenreId()==genreId).findFirst().get();

        return formatBook(book, author, genre);
    }

    private String getBook(Book book) {
        long authorId = book.getAuthorId();
        long genreId = book.getGenreId();

        Author author = authorDao.getAuthorById(authorId);
        Genre genre = genreDao.getGenreById(genreId);

        return formatBook(book, author, genre);
    }

    private String formatBook(Book book, Author author, Genre genre) {
        return String.join(" ",
                String.valueOf(book.getBookId()),
                book.getName(),
                author.getSurname(),
                author.getName().substring(0,1),
                author.getMiddlename().substring(0,1),
                genre.getName(),
                "\n");
    }

    @ShellMethod(value = "Delete book by id")
    public String delBookById(@ShellOption long id) {
        bookDao.deleteById(id);
        return "book deleted";
    }

    @ShellMethod(value = "Get book by id")
    public String getBookById(@ShellOption long id) {
        Book book = bookDao.getBookById(id);
        return getBook(book);
    }
}
