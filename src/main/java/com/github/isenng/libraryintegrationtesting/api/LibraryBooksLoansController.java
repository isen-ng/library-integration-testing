package com.github.isenng.libraryintegrationtesting.api;

import com.github.isenng.libraryintegrationtesting.api.models.LibraryBookLoanModel;
import com.github.isenng.libraryintegrationtesting.api.models.NewLibraryBookLoanModel;
import com.github.isenng.libraryintegrationtesting.service.library.LibraryService;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.AlreadyCheckedOutException;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.BookDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/library/books/loans")
public class LibraryBooksLoansController {
    private LibraryService libraryService;

    public LibraryBooksLoansController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LibraryBookLoanModel post(@RequestBody NewLibraryBookLoanModel newLibraryBookLoan) {
        try {
            return new LibraryBookLoanModel(
                    libraryService.checkOut(newLibraryBookLoan.getBookId()));
        }
        catch (BookDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book does not exist", e);
        }
        catch (AlreadyCheckedOutException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Already checked out", e);
        }
    }
}
