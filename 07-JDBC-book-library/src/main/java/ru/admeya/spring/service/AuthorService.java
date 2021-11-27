package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.jpa.AuthorRepository;
import ru.admeya.spring.jpa.AuthorRepositoryJpa;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepositoryJpa authorDaoJdbc) {
        this.authorRepository = authorDaoJdbc;
    }

    public int countAuthors() {
        return authorRepository.getCount();
    }

    @Transactional
    public Author insertAuthor(String name, String middleName, String surname) {
        return authorRepository.save(new Author(name, middleName, surname));
    }

    @Transactional
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional
    public Author getAuthorById(long id) {
        return authorRepository.findById(id).get();
    }

    @Transactional
    public void delAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Author getAuthorByFIO(
            String name,
            String middlename,
            String surname
    ) {
        Author author;
        List<Author> authors = authorRepository.findByFIO(name, middlename, surname);
        if (authors.isEmpty()) {
            author = new Author(name, middlename, surname);
        } else {
            author = authors.get(0);
        }
        return author;
    }
}
