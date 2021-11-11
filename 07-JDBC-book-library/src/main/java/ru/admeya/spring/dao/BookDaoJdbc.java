package ru.admeya.spring.dao;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.mappers.BookMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public BookDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }


    @Override
    public void insert(Book book) {
        Map<String, Object> params = Map.of(
                "author_id", book.getAuthorId(),
                "genre_id", book.getGenreId(),
                "name", book.getName()
        );

        namedParameterJdbcOperations
                .update("insert into books (author_id, genre_id, `name`) values (:author_id, :genre_id, :name)",
                        params);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from books where book_id = :id", params
        );
    }

    @Override
    public List<Book> getAllBooks() {
        return namedParameterJdbcOperations
                .query("select book_id, author_id, genre_id, `name` from books", new BookMapper());
    }

    @Override
    public Book getBookById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select book_id, author_id, genre_id, `name` from books where book_id = :id ", params, new BookMapper());
    }
}
