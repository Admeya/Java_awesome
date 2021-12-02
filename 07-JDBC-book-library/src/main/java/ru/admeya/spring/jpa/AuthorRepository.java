package ru.admeya.spring.jpa;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Author;

import java.util.List;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    @Override
    List<Author> findAll();

    List<Author> findByNameAndMiddlenameAndSurname(String name, String middlename, String surname);
}
