package com.polarbookshop.catalogservice.bootstrap;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("testdata")
public class BookDataLoader {

    private final BookRespository bookRespository;

    public BookDataLoader(BookRespository bookRespository) {
        this.bookRespository = bookRespository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadData(){
        var book1 = new Book("1234567890","Book1","Author1",12.99);
        var book2 = new Book("0987654321","Book2","Author2",13.99);
        bookRespository.save(book1);
        bookRespository.save(book2);
    }

}
