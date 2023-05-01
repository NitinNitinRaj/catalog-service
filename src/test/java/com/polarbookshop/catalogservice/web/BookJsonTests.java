package com.polarbookshop.catalogservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.entities.Book;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
public class BookJsonTests {

  @Autowired
  private JacksonTester<Book> json;

  @Test
  void testSerialize() throws Exception {
    Book book = new Book(
      21L,
      "1234567890",
      "Book Title",
      "Author Name",
      100.0,
      2
    );
    var jsonContent = json.write(book);
    assertThat(jsonContent)
      .extractingJsonPathStringValue("@.isbn")
      .isEqualTo(book.isbn());
    assertThat(jsonContent)
      .extractingJsonPathStringValue("@.title")
      .isEqualTo(book.title());
    assertThat(jsonContent)
      .extractingJsonPathStringValue("@.author")
      .isEqualTo(book.author());
    assertThat(jsonContent)
      .extractingJsonPathNumberValue("@.price")
      .isEqualTo(book.price());
    assertThat(jsonContent)
      .extractingJsonPathNumberValue("@.id")
      .isEqualTo(book.id().intValue());
    assertThat(jsonContent)
      .extractingJsonPathNumberValue("@.version")
      .isEqualTo(book.version());
  }

  @Test
  void testDeserialize() throws IOException {
    var content =
      """
            {   "id":21,
                "isbn":"1234567890",
                "title":"Book Title",
                "author":"Author Name",
                "price":100.0,
                "version":1
            }
            """;
    assertThat(json.parse(content))
      .usingRecursiveComparison()
      .isEqualTo(
        new Book(21L, "1234567890", "Book Title", "Author Name", 100.0, 1)
      );
  }
}
