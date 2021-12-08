package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public long countAuthors() {
        return authorRepository.count();
    }

    @Transactional
    public Author insertAuthor(String name, String middleName, String surname) {
        return authorRepository.save(new Author(name, middleName, surname));
    }

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(long id) {
        return authorRepository.findById(id).get();
    }

    @Transactional
    public void delAuthorById(long id) {
        authorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Author getAuthorByFIO(
            String name,
            String middlename,
            String surname
    ) {
        Author author;
        List<Author> authors = authorRepository.findByNameAndMiddlenameAndSurname(name, middlename, surname);
        if (authors.isEmpty()) {
            author = authorRepository.save(new Author(name, middlename, surname));
        } else {
            author = authors.get(0);
        }
        return author;
    }
}
