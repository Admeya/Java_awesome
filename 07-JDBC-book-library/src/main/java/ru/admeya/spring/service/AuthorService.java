package ru.admeya.spring.service;

import ru.admeya.spring.domain.Author;

import java.util.List;

public interface AuthorService {

    long countAuthors();

    Author insertAuthor(String name, String middleName, String surname);

    List<Author> getAllAuthors();

    Author getAuthorById(long id);

    void delAuthorById(long id);

    Author getAuthorByFIO(String name, String middlename, String surname);
}
