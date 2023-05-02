package com.polarbookshop.catalogservice;

import static org.assertj.core.api.Assertions.assertThat;

import com.polarbookshop.catalogservice.domain.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

  @Autowired
  private WebTestClient webTestClient;

  @BeforeEach
  void setUp() {
    var expectedBook = Book.of(
      "1478523698",
      "Spring Boot",
      "Nitin",
      129.9,
      "publisher"
    );
    webTestClient
      .post()
      .uri("/api/v1/books")
      .bodyValue(expectedBook)
      .exchange();
  }

  @Test
  void contextLoads() {}

  @Test
  void whenPostThenBookCreated() {
    var expectedBook = Book.of(
      "1592587412",
      "Spring Cloud Native",
      "Raj",
      29.9,
      "publisher"
    );
    webTestClient
      .post()
      .uri("/api/v1/books")
      .bodyValue(expectedBook)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody(Book.class)
      .value(actualValue -> {
        assertThat(actualValue).isNotNull();
        assertThat(actualValue.isbn()).isEqualTo(expectedBook.isbn());
      });
  }

  @Test
  void whenPostSameBookThenNotCreated() {
    var expectedBook = Book.of(
      "1478523698",
      "Spring Boot",
      "Nitin",
      129.9,
      "publisher"
    );

    webTestClient
      .post()
      .uri("/api/v1/books")
      .bodyValue(expectedBook)
      .exchange()
      .expectStatus()
      .is4xxClientError();
  }

  @Test
  void whenGetThenBooksReturned() {
    webTestClient
      .get()
      .uri("/api/v1/books")
      .exchange()
      .expectStatus()
      .isOk()
      .expectBodyList(Book.class)
      .value(bookList -> {
        assertThat(bookList).isNotEmpty();
      });
  }

  @Test
  void whenGetSearchThenReturnBook() {
    webTestClient
      .get()
      .uri("/api/v1/books/1478523698")
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody(Book.class)
      .value(book -> {
        assertThat(book.isbn()).isEqualTo("1478523698");
      });
  }

  @Test
  void whenGetSearchWrongThenReturnNoBook() {
    webTestClient
      .get()
      .uri("/api/v1/books/147888523698")
      .exchange()
      .expectStatus()
      .isNotFound()
      .expectBody(Book.class)
      .value(book -> {
        assertThat(book.isbn()).isNull();
      });
  }

  @Test
  void whenDeleteNoContent() {
    String isbn = "1478523698";
    webTestClient
      .delete()
      .uri("/api/v1/books/" + isbn)
      .exchange()
      .expectStatus()
      .isNoContent();

    webTestClient
      .get()
      .uri("/api/v1/books/" + isbn)
      .exchange()
      .expectStatus()
      .isNotFound()
      .expectBody(String.class)
      .value(errorMessage -> {
        assertThat(errorMessage)
          .contains("Book with id " + isbn + " not found");
      });
  }

  @Test
  void whenPutThenBookEdited() {
    var expectedBook = Book.of(
      "1478523698",
      "Spring Boot",
      "Nitin Raj",
      130.0,
      "publisher"
    );
    webTestClient
      .put()
      .uri("/api/v1/books/1478523698")
      .bodyValue(expectedBook)
      .exchange()
      .expectStatus()
      .isOk()
      .expectBody(Book.class)
      .value(actualValue -> {
        assertThat(actualValue).isNotNull();
        assertThat(actualValue.isbn()).isEqualTo(expectedBook.isbn());
        assertThat(actualValue.author()).isEqualTo(expectedBook.author());
        assertThat(actualValue.price()).isEqualTo(expectedBook.price());
      });
  }

  @Test
  void whenPutWithUnkownThenBookCreated() {
    var expectedBook = Book.of(
      "7894561230",
      "Jungle Book",
      "Mogli",
      10.99,
      "publisher"
    );
    webTestClient
      .put()
      .uri("/api/v1/books/7894561230")
      .bodyValue(expectedBook)
      .exchange()
      .expectStatus()
      .isCreated()
      .expectBody(Book.class)
      .value(actualValue -> {
        assertThat(actualValue).isNotNull();
        assertThat(actualValue.isbn()).isEqualTo(expectedBook.isbn());
        assertThat(actualValue.author()).isEqualTo(expectedBook.author());
        assertThat(actualValue.price()).isEqualTo(expectedBook.price());
      });
  }
}
