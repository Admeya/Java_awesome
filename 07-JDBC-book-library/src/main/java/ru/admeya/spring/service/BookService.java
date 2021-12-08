package ru.admeya.spring.service;

import ru.admeya.spring.domain.Book;

import java.util.List;

public interface BookService {

    Book insertBook(
            String name,
            String middleName,
            String surname,
            String bookName,
            String genreName,
            String comment);

    List<Book> getAllBooks();

    void delBookById(long id);

    Book getBookById(long id);

}
