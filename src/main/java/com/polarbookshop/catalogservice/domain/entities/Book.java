package com.polarbookshop.catalogservice.domain.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record Book(
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
  Double price
) {}
