package ru.admeya.spring.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.stereotype.Component;
import ru.admeya.spring.domain.Author;
import ru.admeya.spring.repository.BookRepository;

@Component
@RequiredArgsConstructor
public class MongoAuthorCascadeDeleteEventListener extends AbstractMongoEventListener<Author> {

    private final BookRepository bookRepository;

    @Override
    public void onAfterDelete(AfterDeleteEvent<Author> event){
        super.onAfterDelete(event);
        bookRepository.removeAuthorById(event.getSource().get("_id").toString());
    }
}
