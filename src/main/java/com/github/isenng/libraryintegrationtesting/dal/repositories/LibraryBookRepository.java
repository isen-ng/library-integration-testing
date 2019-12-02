package com.github.isenng.libraryintegrationtesting.dal.repositories;

import com.github.isenng.libraryintegrationtesting.dal.entities.LibraryBook;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface LibraryBookRepository extends CrudRepository<LibraryBook, Long> {
    List<LibraryBook> findByDueDateLessThan(LocalDate dueDate);
    LibraryBook findByBookId(long bookId);
}
