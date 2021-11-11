package ru.admeya.spring.dao;

import ru.admeya.spring.domain.Book;

import java.util.List;

public interface BookDao {

    void insert(Book author);

    void deleteById(long id);

    List<Book> getAllBooks();

    Book getBookById(long id);
}
