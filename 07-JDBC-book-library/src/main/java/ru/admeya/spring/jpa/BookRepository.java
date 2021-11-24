package ru.admeya.spring.jpa;

import ru.admeya.spring.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Book save(Book book);

    Optional<Book> findById(long id);

    List<Book> getAllBooks();

    void deleteById(long id);
}
