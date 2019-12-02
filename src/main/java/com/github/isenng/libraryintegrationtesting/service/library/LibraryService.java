package com.github.isenng.libraryintegrationtesting.service.library;

import com.github.isenng.libraryintegrationtesting.dal.entities.LibraryBook;
import com.github.isenng.libraryintegrationtesting.dal.repositories.LibraryBookRepository;
import com.github.isenng.libraryintegrationtesting.service.library.duedates.DueDateCalculator;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.AlreadyCheckedOutException;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.BookDoesNotExistException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {
    private LibraryBookRepository repository;
    private DueDateCalculator dueDateCalculator;

    public LibraryService(LibraryBookRepository repository, DueDateCalculator dueDateCalculator) {
        this.repository = repository;
        this.dueDateCalculator = dueDateCalculator;
    }

    public LibraryBook checkOut(long bookId) throws AlreadyCheckedOutException, BookDoesNotExistException {
        LibraryBook book = repository.findByBookId(bookId);
        if (book == null) {
            long dueDate = dueDateCalculator.calculateDueDate();

            try {
                return repository.save(new LibraryBook(dueDate, bookId));
            }
            catch (DataIntegrityViolationException e) {
                throw new BookDoesNotExistException(bookId + " does not exist", e);
            }
        }
        else {
            if (book.getDueDate() != null) {
                throw new AlreadyCheckedOutException();
            }
            else {
                book.setDueDate(dueDateCalculator.calculateDueDate());
                return repository.save(book);
            }
        }
    }
}
