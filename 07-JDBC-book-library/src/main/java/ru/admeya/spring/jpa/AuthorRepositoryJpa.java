package ru.admeya.spring.jpa;

import org.springframework.stereotype.Repository;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.domain.Book;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    public int getCount() {
        Query query = em.createQuery("SELECT COUNT(a) FROM Author a");
        return ((Number) query.getSingleResult()).intValue();
    }

    @Override
    public Author save(Author author) {

        if (author.getId() == 0) {
            em.persist(author);
            return author;
        }
        return em.merge(author);
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Author s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select s from Author s ", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        Author author = em.find(Author.class, id);
        return Optional.ofNullable(author);
    }

    @Override
    public List<Author> findByFIO(String name, String middlename, String surname) {
        TypedQuery<Author> query = em.createQuery("select s from Author s where s.name = :name " +
                "and s.middlename = :middlename and s.surname = :surname", Author.class);
        query.setParameter("name", name);
        query.setParameter("middlename", middlename);
        query.setParameter("surname", surname);
        return query.getResultList();
    }
}
