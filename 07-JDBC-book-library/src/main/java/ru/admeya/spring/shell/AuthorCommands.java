package ru.admeya.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.admeya.spring.dao.AuthorDao;
import ru.admeya.spring.dao.AuthorDaoJdbc;
import ru.admeya.spring.domain.Author;

@ShellComponent
public class AuthorCommands {

    private final AuthorDao authorDao;

    public AuthorCommands(AuthorDaoJdbc authorDaoJdbc) {
        this.authorDao = authorDaoJdbc;
    }

   @ShellMethod(value = "Authors count")
    public int countAuthors() {
        return authorDao.getCount();
    }

   @ShellMethod(value = "Add author")
    public String insertAuthor(
            @ShellOption String name,
            @ShellOption String middleName,
            @ShellOption String surname) {
        long id = authorDao.insert(new Author(name, middleName, surname));
        return "author add:  " + surname + " " + name + " " + middleName + " with id: " + id;
    }

    @ShellMethod(value = "Get all authors")
    public String getAllAuthors() {
        StringBuilder sb = new StringBuilder();
        authorDao.getAllAuthors().forEach(author -> sb.append(author));
        return sb.toString();
    }

    @ShellMethod(value = "Get author by id")
    public String getAuthorById(@ShellOption long id) {
        Author author = authorDao.getAuthorById(id);
        return author.toString();
    }

    @ShellMethod(value = "Delete author by id")
    public String delAuthorById(@ShellOption long id) {
        authorDao.deleteById(id);
        return "author deleted";
    }

    @ShellMethod(value = "Get author by FIO")
    public String getAuthorByFIO(
            @ShellOption String name,
            @ShellOption String middlename,
            @ShellOption String surname
            ) {
        Author author = authorDao.getAuthorByFIO(surname, middlename, name);
        if (author == null) {
            return "Author not found";
        }
        return author.toString();
    }
}
