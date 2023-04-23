package com.polarbookshop.catalogservice.domain.repositories;

import com.polarbookshop.catalogservice.domain.entities.Book;
import java.util.Optional;

public interface BookRespository {
  Iterable<Book> findAll();
  Optional<Book> findByIsbn(String isbn);
  boolean existsByIsbn(String isbn);
  Book save(Book book);
  void deleteByIsbn(String isbn);
}
