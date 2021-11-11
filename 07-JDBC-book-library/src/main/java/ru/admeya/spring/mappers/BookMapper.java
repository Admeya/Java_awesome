package ru.admeya.spring.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {


    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        long book_id = rs.getLong("book_id");
        long author_id = rs.getLong("author_id");
        long genre_id = rs.getLong("genre_id");
        String name = rs.getString("name");

        return new Book(book_id, author_id, genre_id, name);
    }
}
