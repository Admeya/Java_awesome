package ru.admeya.spring.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "middlename")
    private String middlename;

    @Column(name = "surname")
    private String surname;

    public Author() {
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
