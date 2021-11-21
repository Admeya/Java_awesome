package ru.admeya.spring.jpa;

import ru.admeya.spring.domain.Author;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Author save(Author author);
    Optional<Author> findById(long id);

    List<Author> findAll();
    List<Author> findByFIO(String name, String middlename, String surname);

    int getCount();
    void deleteById(long id);
}
