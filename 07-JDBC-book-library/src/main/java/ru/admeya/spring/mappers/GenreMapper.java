package ru.admeya.spring.mappers;

import org.springframework.jdbc.core.RowMapper;
import ru.admeya.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {


    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {

        long id = rs.getLong("genre_id");
        String name = rs.getString("name");

        return new Genre(id, name);
    }
}
