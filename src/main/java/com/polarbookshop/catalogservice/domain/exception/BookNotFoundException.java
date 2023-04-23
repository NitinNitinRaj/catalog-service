package com.polarbookshop.catalogservice.domain.exception;

public class BookNotFoundException extends RuntimeException {

  public BookNotFoundException(String isbn) {
    super("Book with id " + isbn + " not found");
  }
}
