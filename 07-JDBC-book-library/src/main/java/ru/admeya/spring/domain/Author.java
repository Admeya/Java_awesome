package ru.admeya.spring.domain;

import lombok.Data;

@Data
public class Author {

    private long id;

    private String name;

    private String middlename;

    private String surname;

    public Author(long id, String name, String middleName, String surname) {
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
