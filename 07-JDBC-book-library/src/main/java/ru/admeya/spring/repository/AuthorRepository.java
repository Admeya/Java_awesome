package ru.admeya.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.admeya.spring.domain.Author;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findByNameAndMiddlenameAndSurname(String name, String middlename, String surname);
}
