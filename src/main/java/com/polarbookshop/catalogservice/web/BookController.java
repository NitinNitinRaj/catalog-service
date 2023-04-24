package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.services.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public Iterable<Book> getAll() {
    return bookService.viewBookList();
  }

  @GetMapping("/{isbn}")
  public Book getByIsbn(@PathVariable("isbn") String isbn) {
    return bookService.viewBookDetails(isbn);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Book post(@Valid @RequestBody Book book) {
    return bookService.addBookToCatalog(book);
  }

  @PutMapping("/{isbn}")
  public ResponseEntity<Book> put(
    @PathVariable("isbn") String isbn,
    @Valid @RequestBody Book book
  ) {
    if (bookService.bookPresent(isbn)) {
      return new ResponseEntity<Book>(
        bookService.editBookDetails(isbn, book),
        null,
        HttpStatus.OK
      );
    }
    return new ResponseEntity<Book>(
      bookService.editBookDetails(isbn, book),
      null,
      HttpStatus.CREATED
    );
  }

  @DeleteMapping("/{isbn}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("isbn") String isbn) {
    bookService.removeBookFromCatalog(isbn);
  }
}
