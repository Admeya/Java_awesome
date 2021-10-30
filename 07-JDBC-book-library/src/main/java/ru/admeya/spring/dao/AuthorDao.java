package ru.admeya.spring.dao;

import ru.admeya.spring.domain.Author;

import java.util.List;

public interface AuthorDao {

    int getCount();

    long insert(Author author);

    void deleteById(long id);

    List<Author> getAllAuthors();

    Author getAuthorById(long id);

    Author getAuthorByFIO(String surname, String middlename, String name);
}
