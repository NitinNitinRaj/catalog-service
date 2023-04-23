package com.polarbookshop.catalogservice.domain.exception;

public class BookAlreadyExitsException extends RuntimeException {

  public BookAlreadyExitsException(String isbn) {
    super("Book with ISBN " + isbn + " already exists");
  }
}
