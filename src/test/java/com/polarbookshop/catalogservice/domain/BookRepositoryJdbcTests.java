package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;

@DataJdbcTest
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("integration")
public class BookRepositoryJdbcTests {

  @Autowired
  private BookRespository bookRespository;

  @Autowired
  private JdbcAggregateTemplate jdbcAggregateTemplate;

  @Test
  public void testFindBookByIsbnWhenExisting() {
    var bookIsbn = "1234567890";
    var book = Book.of(bookIsbn, "title", "Author", 12.99);
    jdbcAggregateTemplate.insert(book);
    Optional<Book> dbBook = bookRespository.findByIsbn(bookIsbn);
    assertThat(dbBook).isPresent();
    assertThat(dbBook.get().isbn()).isEqualTo(bookIsbn);
  }
}