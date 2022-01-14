package ru.admeya.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.admeya.spring.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
