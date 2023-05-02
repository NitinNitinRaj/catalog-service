package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.exception.BookAlreadyExitsException;
import com.polarbookshop.catalogservice.domain.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;
import com.polarbookshop.catalogservice.domain.services.BookServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {

  @Mock
  private BookRespository bookRespository;

  @InjectMocks
  private BookServiceImpl bookService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    bookService = new BookServiceImpl(bookRespository);
  }

  @Test
  void whenBookAlreadyExistsThenThrow() {
    String isbn = "1234567890";
    var book = Book.of(isbn, "Boot", "Nitin", 22.3,"publisher");
    when(bookRespository.existsByIsbn(isbn)).thenReturn(true);
    assertThatThrownBy(() -> bookService.addBookToCatalog(book))
      .isInstanceOf(BookAlreadyExitsException.class)
      .hasMessage("Book with ISBN " + isbn + " already exists");
  }

  @Test
  void whenBookNotFoundThenThrow() {
    String isbn = "1234567890";
    when(bookRespository.findByIsbn(isbn)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
      .isInstanceOf(BookNotFoundException.class)
      .hasMessage("Book with id " + isbn + " not found");
  }
}
