package com.polarbookshop.catalogservice.domain.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {

  private List<String> message;

  @JsonFormat(shape = Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
  private LocalDateTime timestamp;

  public ErrorResponse(List<String> message) {
    this.message = message;
    this.timestamp = LocalDateTime.now();
  }
}
