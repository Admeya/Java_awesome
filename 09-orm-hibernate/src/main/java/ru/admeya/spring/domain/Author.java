package ru.admeya.spring.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String name;

    private String middlename;

    private String surname;

    @DBRef
    private List<Book> books;

    public Author() {
    }

    public Author(String id, String name, String middleName, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.middlename = middleName;
    }

    public Author(String name, String middleName, String surname) {
        this.name = name;
        this.surname = surname;
        this.middlename = middleName;
    }

    @Override
    public String toString() {
        return String.join(" ", String.valueOf(id), name, middlename, surname, "\n");
    }
}
