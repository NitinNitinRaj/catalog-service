package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.config.DataConfig;
import com.polarbookshop.catalogservice.domain.entities.Book;
import com.polarbookshop.catalogservice.domain.repositories.BookRespository;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.test.context.ActiveProfiles;

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

  @Test
  public void testFindByIsbnWhenNotExisiting() {
    var bookIsbn = "1234567890";
    assertThat(bookRespository.findByIsbn(bookIsbn)).isEmpty();
  }

  @Test
  public void testExistsByIsbnWhenExisting() {
    var bookIsbn = "1234567890";
    var book = Book.of(bookIsbn, "title", "Author", 12.99);
    jdbcAggregateTemplate.insert(book);
    boolean isExisiting = bookRespository.existsByIsbn(bookIsbn);
    assertThat(isExisiting).isTrue();
  }

  @Test
  public void testExistsByIsbnWhenNotExisiting() {
    var bookIsbn = "1234567890";
    assertThat(bookRespository.existsByIsbn(bookIsbn)).isFalse();
  }

  @Test
  public void deleteByIsbn() {
    var bookIsbn = "1234567890";
    var book = Book.of(bookIsbn, "title", "Author", 12.99);
    var dbBook = jdbcAggregateTemplate.insert(book);
    bookRespository.deleteByIsbn(bookIsbn);
    assertThat(jdbcAggregateTemplate.findById(dbBook.id(), Book.class))
      .isNull();
  }

  @Test
  public void findAll() {
    var bookIsbn1 = "1234567890";
    var book1 = Book.of(bookIsbn1, "title", "Author", 12.99);
    var bookIsbn2 = "1234567899";
    var book2 = Book.of(bookIsbn2, "title2", "Author2", 13.99);
    jdbcAggregateTemplate.insert(book1);
    jdbcAggregateTemplate.insert(book2);

    Iterable<Book> findAll = bookRespository.findAll();

    assertThat(
      StreamSupport
        .stream(findAll.spliterator(), true)
        .filter(book ->
          book.isbn().equals(bookIsbn1) || book.isbn().equals(bookIsbn2)
        )
        .toList()
    )
      .hasSize(2);
  }
}
