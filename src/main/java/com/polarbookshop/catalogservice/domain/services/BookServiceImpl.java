package com.polarbookshop.catalogservice.domain.services;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.exception.BookAlreadyExitsException;
import com.polarbookshop.catalogservice.domain.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

  private final BookRespository bookRespository;

  public BookServiceImpl(BookRespository bookRespository) {
    this.bookRespository = bookRespository;
  }

  @Override
  public Iterable<Book> viewBookList() {
    return bookRespository.findAll();
  }

  @Override
  public Book viewBookDetails(String isbn) {
    return bookRespository
      .findByIsbn(isbn)
      .orElseThrow(() -> new BookNotFoundException(isbn));
  }

  @Override
  public Book addBookToCatalog(Book book) {
    if (bookRespository.existsByIsbn(book.isbn())) {
      throw new BookAlreadyExitsException(book.isbn());
    }
    return bookRespository.save(book);
  }

  @Override
  public void removeBookFromCatalog(String isbn) {
    bookRespository.deleteByIsbn(isbn);
  }

  @Override
  public Book editBookDetails(String isbn, Book book) {
    return bookRespository
      .findByIsbn(isbn)
      .map(existingBook -> {
        var updatedBook = new Book(
          existingBook.isbn(),
          book.title(),
          book.author(),
          book.price()
        );
        return bookRespository.save(updatedBook);
      })
      .orElseGet(() -> addBookToCatalog(book));
  }

  @Override
  public boolean bookPresent(String isbn) {
    return bookRespository.existsByIsbn(isbn);
  }
}
