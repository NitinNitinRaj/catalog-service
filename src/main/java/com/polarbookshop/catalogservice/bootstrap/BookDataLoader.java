package com.polarbookshop.catalogservice.bootstrap;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;

import java.util.List;

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
        bookRespository.deleteAll();
        var book1 = Book.of("1234567890","Book1","Author1",12.99,"publisher");
        var book2 = Book.of("0987654321","Book2","Author2",13.99,"publisher");
        bookRespository.saveAll(List.of(book1,book2));
    }

}
