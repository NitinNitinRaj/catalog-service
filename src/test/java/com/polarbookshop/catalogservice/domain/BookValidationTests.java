package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.polarbookshop.catalogservice.domain.entities.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookValidationTests {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  void noConstraintsAreVoilated() {
    Book book = Book.of("1234567890", "Book Title", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).isEmpty();
  }

  @Test
  void isbnConstraintVoilated() {
    Book book = Book.of("123456789", "Book Title", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).hasSize(1);
    assertEquals(
      violations.iterator().next().getMessage(),
      "ISBN format must be valid"
    );
  }

  @Test
  void isbnNotDefinedValidationFailed() {
    Book book = Book.of("", "Book Title", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(
      violations.stream().map(ConstraintViolation::getMessage).toList()
    )
      .contains("ISBN must be defined")
      .contains("ISBN format must be valid");
  }

  @Test
  void titleNotDefinedValidationFailed() {
    Book book = Book.of("1234567890", "", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations.iterator().next().getMessage())
      .isEqualTo("Title must be defined");
  }

  @Test
  void authorNotDefinedValidationFailed() {
    Book book = Book.of("1234567890", "Book Title", "", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations.iterator().next().getMessage())
      .isEqualTo("Author must be defined");
  }

  @Test
  void priceNullValidationFailed() {
    Book book = Book.of("1234567890", "Book Title", "Author Name", null);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(
      violations.stream().map(ConstraintViolation::getMessage).toList()
    )
      .contains("Price must be defined");
  }

  @Test
  void negativePriceValidationFailed() {
    Book book = Book.of("1234567890", "Book Title", "Author Name", -100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(
      violations.stream().map(ConstraintViolation::getMessage).toList()
    )
      .contains("Price must be greater than zero");
  }

  @Test
  void zeroPriceValidationFailed() {
    Book book = Book.of("1234567890", "Book Title", "Author Name", 0.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(
      violations.stream().map(ConstraintViolation::getMessage).toList()
    )
      .contains("Price must be greater than zero");
  }
}
