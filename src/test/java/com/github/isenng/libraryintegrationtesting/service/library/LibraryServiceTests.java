package com.github.isenng.libraryintegrationtesting.service.library;

import com.github.isenng.libraryintegrationtesting.dal.entities.Book;
import com.github.isenng.libraryintegrationtesting.dal.entities.LibraryBook;
import com.github.isenng.libraryintegrationtesting.dal.repositories.LibraryBookRepository;
import com.github.isenng.libraryintegrationtesting.service.library.LibraryService;
import com.github.isenng.libraryintegrationtesting.service.library.duedates.DueDateCalculator;
import com.github.isenng.libraryintegrationtesting.service.library.exceptions.BookDoesNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class LibraryServiceTests {
    private LibraryService libraryService;
    private LibraryBookRepository mockLibraryBookRepository;
    private DueDateCalculator mockDueDateCalculator;

    @BeforeEach
    void setup() {
        mockLibraryBookRepository = mock(LibraryBookRepository.class);
        mockDueDateCalculator = mock(DueDateCalculator.class);
        libraryService = new LibraryService(mockLibraryBookRepository, mockDueDateCalculator);
    }

    @Nested
    class CheckOut {
        @Test
        void shouldThrowBookDoesNotExistIfSavingFailsWithDataIntegrityException() {
            // arrange
            long bookId = 9;
            when(mockLibraryBookRepository.findByBookId(bookId))
                    .thenReturn(null);
            when(mockDueDateCalculator.calculateDueDate())
                    .thenReturn(Instant.now().getEpochSecond());
            when(mockLibraryBookRepository.save(any()))
                    .thenThrow(new DataIntegrityViolationException("abc"));

            // act
            Throwable t = catchThrowable(() -> libraryService.checkOut(bookId));

            // assert
            assertThat(t).isInstanceOf(BookDoesNotExistException.class);
        }

        @Test
        void shouldSaveBookIfBookHasNotBeenCheckedOutBefore() throws Exception {
            // arrange
            long bookId = 9;
            long dueDate = Instant.now().getEpochSecond();
            when(mockLibraryBookRepository.findByBookId(bookId))
                    .thenReturn(null);
            when(mockDueDateCalculator.calculateDueDate())
                    .thenReturn(Instant.now().getEpochSecond());

            LibraryBook expected = new LibraryBook(123, dueDate, bookId);
            when(mockLibraryBookRepository.save(any()))
                    .thenReturn(expected);

            // act
            LibraryBook result = libraryService.checkOut(bookId);

            // assert
            assertThat(result.getId()).isEqualTo(expected.getId());
            assertThat(result.getDueDate()).isEqualTo(expected.getDueDate());
            assertThat(result.getBook().getId()).isEqualTo(expected.getBook().getId());

            ArgumentCaptor<LibraryBook> libraryBookArgumentCaptor = ArgumentCaptor.forClass(LibraryBook.class);
            verify(mockLibraryBookRepository).save(libraryBookArgumentCaptor.capture());

            assertThat(libraryBookArgumentCaptor.getValue().getDueDate()).isEqualTo(dueDate);
            assertThat(libraryBookArgumentCaptor.getValue().getBook().getId()).isEqualTo(bookId);
        }
    }
}
