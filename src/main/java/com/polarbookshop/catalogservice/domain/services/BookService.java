package com.polarbookshop.catalogservice.domain.services;

import com.polarbookshop.catalogservice.domain.entities.Book;

public interface BookService {
  Iterable<Book> viewBookList();
  Book viewBookDetails(String isbn);
  Book addBookToCatalog(Book book);
  void removeBookFromCatalog(String isbn);
  Book editBookDetails(String isbn, Book book);
}
