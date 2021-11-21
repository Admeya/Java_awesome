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

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(long id) {
        return authorRepository.findById(id).get();
    }

    public void delAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    public Author getAuthorByFIO(
            String name,
            String middlename,
            String surname
    ) {
        return authorRepository.findByFIO(name, middlename, surname).get(0);
    }
}
