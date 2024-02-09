package ru.admeya.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.admeya.spring.domain.Book;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

    void removeAuthorById(String authorId);
}
