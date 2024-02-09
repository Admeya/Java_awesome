package ru.admeya.spring.service;

import org.springframework.stereotype.Service;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public long countAuthors() {
        return authorRepository.count();
    }

    public Author insertAuthor(String name, String middleName, String surname) {
        return authorRepository.save(new Author(name, middleName, surname));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(String id) {
        return authorRepository.findById(id).get();
    }

    public void delAuthorById(String id) {
        authorRepository.deleteById(id);
    }

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
