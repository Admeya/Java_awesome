package ru.admeya.spring.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.admeya.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorMapper implements RowMapper<Author> {


    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("author_id");
        String name = rs.getString("name");
        String middlename = rs.getString("middlename");
        String surname = rs.getString("surname");
        return new Author(id, name, middlename, surname);
    }
}
