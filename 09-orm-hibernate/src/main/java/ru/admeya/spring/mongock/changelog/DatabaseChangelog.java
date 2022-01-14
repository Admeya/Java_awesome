package ru.admeya.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.GenreRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "bykovaie", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertGenre", author = "bykovaie")
    public void insertGenre(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("genres");
        var doc1 = new Document().append("name", "sport");
        myCollection.insertOne(doc1);
    }

    @ChangeSet(order = "003", id = "insertBooks", author = "bykovaie")
    public void insertBooks(MongoDatabase db, GenreRepository genreRepository) {
        Genre genre = genreRepository.findByName("sport").orElse(new Genre());

        MongoCollection<Document> myCollection = db.getCollection("books");
        var doc1 = new Document().append("name", "Travelling").append("genre", new Document(genre));
        var doc2 = new Document().append("name", "Alyaska");
        myCollection.insertMany(List.of(doc1, doc2));
    }

    @ChangeSet(order = "004", id = "insertAuthors", author = "bykovaie")
    public void insertAuthors(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("authors");
        var doc1 = new Document().append("name", "John").append("middlename", "Valter").append("surname", "Scott");
        var doc2 = new Document().append("name", "Ivan").append("middlename", "Ivanovich").append("surname", "Ivanov");

        myCollection.insertMany(List.of(doc1, doc2));
    }

    @ChangeSet(order = "005", id = "insertComments", author = "bykovaie")
    public void insertComments(MongoDatabase db) {
        MongoCollection<Document> myCollection = db.getCollection("comments");
        var doc1 = new Document().append("name", "Very good book");
        var doc2 = new Document().append("name", "Not so bad");

        myCollection.insertMany(List.of(doc1, doc2));
    }
}
