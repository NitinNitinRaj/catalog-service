package com.polarbookshop.catalogservice.domain.exception;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BookNotFoundException.class)
  ResponseEntity<ErrorResponse> bookNotFoundHandler(BookNotFoundException ex) {
    return new ResponseEntity<>(
      new ErrorResponse(List.of(ex.getMessage())),
      HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(BookAlreadyExitsException.class)
  ResponseEntity<ErrorResponse> bookAlreadyExistsHandler(
    BookAlreadyExitsException ex
  ) {
    return new ResponseEntity<>(
      new ErrorResponse(List.of(ex.getMessage())),
      HttpStatus.UNPROCESSABLE_ENTITY
    );
  }

  @Override
  @Nullable
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
    MethodArgumentNotValidException ex,
    HttpHeaders headers,
    HttpStatusCode status,
    WebRequest request
  ) {
    List<String> errMsgs = ex
      .getBindingResult()
      .getAllErrors()
      .stream()
      .map(err -> err.getDefaultMessage())
      .toList();

    return new ResponseEntity<>(
      new ErrorResponse(errMsgs),
      HttpStatus.BAD_REQUEST
    );
  }
}
