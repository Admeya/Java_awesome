package ru.admeya.spring.repository;

import org.springframework.data.repository.CrudRepository;
import ru.admeya.spring.domain.Book;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, String> {

    @Override
    List<Book> findAll();
}
