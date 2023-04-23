package com.polarbookshop.catalogservice;

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

public class BookValidationTest {

  private static Validator validator;

  @BeforeAll
  static void setUp() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @Test
  void noConstraintsAreVoilated() {
    Book book = new Book("1234567890", "Book Title", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).isEmpty();
  }

  @Test
  void isbnConstraintVoilated() {
    Book book = new Book("123456789", "Book Title", "Author Name", 100.0);
    Set<ConstraintViolation<Book>> violations = validator.validate(book);
    assertThat(violations).hasSize(1);
    assertEquals( 
      violations.iterator().next().getMessage(),
      "ISBN format must be valid"
    );
  }
}
