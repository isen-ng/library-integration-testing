package com.github.isenng.libraryintegrationtesting.api;

import com.github.isenng.libraryintegrationtesting.api.models.LibraryBookLoanModel;
import com.github.isenng.libraryintegrationtesting.api.models.NewLibraryBookLoanModel;
import com.github.isenng.libraryintegrationtesting.dal.entities.LibraryBook;
import com.github.isenng.libraryintegrationtesting.service.library.LibraryService;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.AlreadyCheckedOutException;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.BookDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LibraryBooksLoansControllerTests {
    private LibraryBooksLoansController controller;
    private LibraryService mockLibraryService;

    @BeforeEach
    void setup() {
        mockLibraryService = mock(LibraryService.class);
        controller = new LibraryBooksLoansController(mockLibraryService);
    }

    @Nested
    class Post {
        @Test
        void shouldThrow404IfLibraryServiceThrowsBookNotFound() throws Exception {
            // arrange
            NewLibraryBookLoanModel model = new NewLibraryBookLoanModel(9);
            when(mockLibraryService.checkOut(model.getBookId()))
                    .thenThrow(new BookDoesNotExistException());

            // act
            Throwable t = catchThrowable(() -> controller.post(model));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void shouldThrow400IfLibraryServiceThrowsAlreadyCheckedOut() throws Exception {
            // arrange
            NewLibraryBookLoanModel model = new NewLibraryBookLoanModel(9);
            when(mockLibraryService.checkOut(model.getBookId()))
                    .thenThrow(new AlreadyCheckedOutException());

            // act
            Throwable t = catchThrowable(() -> controller.post(model));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldReturnLibraryBookLoanIfDataIsValid() throws Exception {
            // arrange
            NewLibraryBookLoanModel model = new NewLibraryBookLoanModel(9);
            LibraryBook expected = new LibraryBook(123, Instant.now().getEpochSecond(), model.getBookId());
            when(mockLibraryService.checkOut(model.getBookId()))
                    .thenReturn(expected);

            // act
            LibraryBookLoanModel result = controller.post(model);

            // assert
            assertThat(result.getId()).isEqualTo(expected.getId());
            assertThat(result.getDueDate()).isEqualTo(expected.getDueDate());
            assertThat(result.getBookId()).isEqualTo(expected.getBook().getId());
        }
    }
}
