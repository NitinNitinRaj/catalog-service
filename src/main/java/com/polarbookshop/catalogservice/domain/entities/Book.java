package com.polarbookshop.catalogservice.domain.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

public record Book(
  @Id Long id,
  @NotBlank(message = "ISBN must be defined")
  @Pattern(
    regexp = "^([0-9]{10}|[0-9]{13})$",
    message = "ISBN format must be valid"
  )
  String isbn,
  @NotBlank(message = "Title must be defined") String title,
  @NotBlank(message = "Author must be defined") String author,
  @NotNull(message = "Price must be defined")
  @Positive(message = "Price must be greater than zero")
  Double price,
  @Version int version
) {
  public static Book of(
    String isbn,
    String title,
    String author,
    Double price
  ) {
    return new Book(null, isbn, title, author, price, 0);
  }
}
