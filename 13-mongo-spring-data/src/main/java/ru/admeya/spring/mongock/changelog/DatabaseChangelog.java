package ru.admeya.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import ru.admeya.spring.domain.Book;
import ru.admeya.spring.domain.Genre;
import ru.admeya.spring.repository.BookRepository;
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

    @ChangeSet(order = "003", id = "insertAuthorsAndBooks", author = "bykovaie")
    public void insertAuthors(MongoDatabase db, GenreRepository genreRepository) {
        MongoCollection<Document> myCollection = db.getCollection("authors");
        var author1 = new Document().append("name", "John").append("middlename", "Valter").append("surname", "Scott");
        var author2 = new Document().append("name", "Ivan").append("middlename", "Ivanovich").append("surname", "Ivanov");

        var authors = List.of(author1, author2);
        myCollection.insertMany(authors);

        Genre genre = genreRepository.findByName("sport").orElse(new Genre());

        MongoCollection<Document> bookCollection = db.getCollection("books");

        var book1 = new Document()
                .append("name", "Travelling")
                .append("genreId", genre.getGenreId())
                .append("authors", authors);
        var book2 = new Document()
                .append("name", "Alyaska")
                .append("genreId", genre.getGenreId())
                .append("authors", List.of(author1));
        bookCollection.insertMany(List.of(book1, book2));
    }


    @ChangeSet(order = "005", id = "insertComments", author = "bykovaie")
    public void insertComments(MongoDatabase db, BookRepository bookRepository) {
        List<Book> books = bookRepository.findAll();
        Book book1 = books.get(0);

        MongoCollection<Document> myCollection = db.getCollection("comments");
        var doc1 = new Document().append("description", "Very good book").append("bookId", book1.getBookId());
        var doc2 = new Document().append("description", "Not so bad").append("bookId", book1.getBookId());

        myCollection.insertMany(List.of(doc1, doc2));
    }
}
