package ru.admeya.spring.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import ru.admeya.spring.domain.Book;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    public BookRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void removeAuthorById(String authorId) {

        Query queryForAuthorId = Query.query(Criteria.where("$id").is(new ObjectId(authorId)));

        mongoTemplate.updateMulti(new Query(), new Update().pull("authors", queryForAuthorId), Book.class);
    };
}
