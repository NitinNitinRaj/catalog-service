package com.polarbookshop.catalogservice.domain.repositories;

import com.polarbookshop.catalogservice.domain.entities.Book;
import java.util.Optional;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface BookRespository extends CrudRepository<Book, Long> {
  Optional<Book> findByIsbn(String isbn);
  boolean existsByIsbn(String isbn);

  @Modifying
  @Transactional
  @Query("delete from book where isbn = :isbn")
  void deleteByIsbn(@Param("isbn") String isbn);
}
