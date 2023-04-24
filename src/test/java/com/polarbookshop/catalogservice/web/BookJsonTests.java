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
    Book book = new Book("1234567890", "Book Title", "Author Name", 100.0);
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
  }

  @Test
  void testDeserialize() throws IOException {
    var content =
      """
            {
                "isbn":"1234567890",
                "title":"Book Title",
                "author":"Author Name",
                "price":"100.0"
            }
            """;
    assertThat(json.parse(content))
      .usingRecursiveComparison()
      .isEqualTo(new Book("1234567890", "Book Title", "Author Name", 100.0));
  }
}
