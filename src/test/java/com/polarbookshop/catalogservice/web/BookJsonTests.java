package com.polarbookshop.catalogservice.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.entities.Book;
import java.io.IOException;
import java.time.Instant;
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
    var now = Instant.now();
    Book book = new Book(
      21L,
      "1234567890",
      "Book Title",
      "Author Name",
      100.0,
      "publisher",
      now,
      now,
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
      .extractingJsonPathStringValue("@.publisher")
      .isEqualTo(book.publisher());
    assertThat(jsonContent)
      .extractingJsonPathNumberValue("@.id")
      .isEqualTo(book.id().intValue());
    assertThat(jsonContent)
      .extractingJsonPathNumberValue("@.version")
      .isEqualTo(book.version());
    assertThat(jsonContent)
      .extractingJsonPathStringValue("@.createdDate")
      .isEqualTo(book.createdDate().toString());
    assertThat(jsonContent)
      .extractingJsonPathStringValue("@.lastModifiedDate")
      .isEqualTo(book.lastModifiedDate().toString());
  }

  @Test
  void testDeserialize() throws IOException {
    var instant = Instant.parse("2021-09-07T22:50:37.135029Z");
    var content =
      """
            {   "id":21,
                "isbn":"1234567890",
                "title":"Book Title",
                "author":"Author Name",
                "price":100.0,
                "publisher":"publisher",
                "createdDate": "2021-09-07T22:50:37.135029Z",
                "lastModifiedDate": "2021-09-07T22:50:37.135029Z",
                "version":1
            }
            """;
    assertThat(json.parse(content))
      .usingRecursiveComparison()
      .isEqualTo(
        new Book(
          21L,
          "1234567890",
          "Book Title",
          "Author Name",
          100.0,
          "publisher",
          instant,
          instant,
          1
        )
      );
  }
}
