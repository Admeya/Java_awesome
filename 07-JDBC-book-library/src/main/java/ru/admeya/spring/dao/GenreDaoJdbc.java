package ru.admeya.spring.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.mappers.GenreMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public GenreDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", genre.getName());

        namedParameterJdbcOperations
                .update("insert into genres (`name`) values (:name)",
                        namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from genres where genre_id = :id", params
        );
    }

    @Override
    public List<Genre> getAllGenres() {
        return namedParameterJdbcOperations.query("select * from genres", new GenreMapper());
    }

    @Override
    public Genre getGenreById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations.queryForObject("select * from genres where genre_id = :id ", params, new GenreMapper());
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Map<String, Object> params = Collections.singletonMap("name", genreName);

        Genre genre = null;
        try {
            genre = namedParameterJdbcOperations.queryForObject("select * from genres where `name` = :name ", params, new GenreMapper());
        } catch (Exception ex) {
        }
        return genre;
    }
}
