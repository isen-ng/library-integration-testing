package com.github.isenng.libraryintegrationtesting.dal.repositories;

import com.github.isenng.libraryintegrationtesting.dal.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthor(String author);
}
