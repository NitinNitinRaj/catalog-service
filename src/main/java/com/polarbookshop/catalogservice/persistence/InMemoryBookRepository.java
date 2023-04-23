package com.polarbookshop.catalogservice.persistence;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookRepository implements BookRespository {

  private static final Map<String, Book> books = new ConcurrentHashMap<>();

  public InMemoryBookRepository() {
    books.put("1234567890", new Book("1234567890", "Book 1", "Author 1",12.88));
    books.put("9876543210", new Book("9876543210", "Book 2", "Author 2",15.99));
  }

  @Override
  public Iterable<Book> findAll() {
    return books.values();
  }

  @Override
  public Optional<Book> findByIsbn(String isbn) {
    return existsByIsbn(isbn) ? Optional.of(books.get(isbn)) : Optional.empty();
  }

  @Override
  public boolean existsByIsbn(String isbn) {
    return books.get(isbn) != null;
  }

  @Override
  public Book save(Book book) {
    books.put(book.isbn(), book);
    return book;
  }

  @Override
  public void deleteByIsbn(String isbn) {
    books.remove(isbn);
  }
}
