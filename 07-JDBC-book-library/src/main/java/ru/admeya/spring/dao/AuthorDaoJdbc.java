package ru.admeya.spring.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.mappers.AuthorMapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public AuthorDaoJdbc(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    public int getCount() {
        return namedParameterJdbcOperations.getJdbcOperations()
                .queryForObject("select count(*) from authors", Integer.class);
    }

    @Override
    public long insert(Author author) {

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", author.getName());
        namedParameters.addValue("middlename", author.getMiddlename());
        namedParameters.addValue("surname", author.getSurname());

        namedParameterJdbcOperations
                .update("insert into authors (`name`, middlename, surname) values (:name, :middlename, :surname)",
                        namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParameterJdbcOperations.update(
                "delete from authors where author_id = :id", params
        );
    }

    @Override
    public List<Author> getAllAuthors() {
        return namedParameterJdbcOperations.query("select author_id, `name`, middlename, surname from authors", new AuthorMapper());
    }

    @Override
    public Author getAuthorById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return namedParameterJdbcOperations
                .queryForObject("select author_id, `name`, middlename, surname  from authors where author_id = :id ", params, new AuthorMapper());
    }

    @Override
    public Author getAuthorByFIO( String name, String middlename, String surname) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("name", name);
        namedParameters.addValue("middlename", middlename);
        namedParameters.addValue("surname", surname);

        Author author = null;

        try {
            author = namedParameterJdbcOperations
                    .queryForObject("select author_id, `name`, middlename, surname from authors " +
                    "where name = :name and middlename = :middlename and surname = :surname ", namedParameters, new AuthorMapper())
            ;
        } catch (Exception ex) {
        }
        return author;
    }
}
