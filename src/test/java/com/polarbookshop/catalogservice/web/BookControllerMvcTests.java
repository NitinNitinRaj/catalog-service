package com.polarbookshop.catalogservice.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.polarbookshop.catalogservice.domain.exception.BookNotFoundException;
import com.polarbookshop.catalogservice.domain.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BookService bookService;

  @Test
  void whenGetBookNotExistingThenShouldReturn404() throws Exception {
    String isbn = "125847936";
    given(bookService.viewBookDetails(isbn))
      .willThrow(new BookNotFoundException(isbn));

    mockMvc
      .perform(get("/api/v1/books/s" + isbn))
      .andExpect(status().isNotFound());
  }
}
