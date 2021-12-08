package ru.admeya.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.admeya.spring.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Genre save(Genre genre) {

        if (genre.getGenreId() == 0) {
            em.persist(genre);
            return genre;
        }
        return em.merge(genre);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Genre s where s.genreId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select s from Genre s ", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        Genre genre = em.find(Genre.class, id);
        return Optional.ofNullable(genre);
    }

    @Override
    public Optional<Genre> findByName(String genreName) {

        TypedQuery<Genre> query = em.createQuery("select s from Genre s where s.name = :name ", Genre.class);
        query.setParameter("name", genreName);
        return Optional.ofNullable(query.getSingleResult());
    }
}
