package com.github.isenng.libraryintegrationtesting.api;

import com.github.isenng.libraryintegrationtesting.api.models.BookModel;
import com.github.isenng.libraryintegrationtesting.api.models.NewBookModel;
import com.github.isenng.libraryintegrationtesting.dal.entities.Book;
import com.github.isenng.libraryintegrationtesting.service.books.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BookControllerTests {
    private BookController controller;
    private BookService mockBookService;

    @BeforeEach
    void setup() {
        mockBookService = mock(BookService.class);
        controller = new BookController(mockBookService);
    }

    @Nested
    class Get {
        @Test
        void shouldThrow404ExceptionIfBookDoesNotExist() {
            // arrange
            long id = 9;
            when(mockBookService.Get(id)).thenReturn(Optional.empty());

            // act
            Throwable t = catchThrowable(() -> controller.get(id));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void shouldReturnBookIfBookExist() {
            // arrange
            long id = 9;
            Book expected = new Book(id, "my_title", "my_author", "my_text");
            when(mockBookService.Get(id)).thenReturn(Optional.of(expected));

            // act
            BookModel result = controller.get(id);

            // assert
            assertThat(result.getId()).isEqualTo(expected.getId());
            assertThat(result.getTitle()).isEqualTo(expected.getTitle());
            assertThat(result.getAuthor()).isEqualTo(expected.getAuthor());
            assertThat(result.getText()).isEqualTo(expected.getText());
        }
    }

    @Nested
    class Post {
        @Test
        void shouldThrow400ExceptionIfTitleIsEmpty() {
            // arrange
            NewBookModel newBookModel = new NewBookModel("", "my_author", "my_text");

            // act
            Throwable t = catchThrowable(() -> controller.post(newBookModel));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldThrow400ExceptionIfAuthorIsEmpty() {
            // arrange
            NewBookModel newBookModel = new NewBookModel("my_title", "", "my_text");

            // act
            Throwable t = catchThrowable(() -> controller.post(newBookModel));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldThrow400ExceptionIfTextIsEmpty() {
            // arrange
            NewBookModel newBookModel = new NewBookModel("my_title", "my_author", "");

            // act
            Throwable t = catchThrowable(() -> controller.post(newBookModel));

            // assert
            assertThat(t).isInstanceOf(ResponseStatusException.class);

            ResponseStatusException responseStatusException = (ResponseStatusException) t;
            assertThat(responseStatusException.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void shouldReturnCreatedBookIfDataIsValid() {
            // arrange
            NewBookModel newBookModel = new NewBookModel("my_title", "my_author", "my_text");
            Book expected = new Book(123, "123", "abc", "qwe");
            when(mockBookService.Insert(newBookModel.getTitle(), newBookModel.getAuthor(), newBookModel.getText()))
                .thenReturn(expected);

            // act
            BookModel result = controller.post(newBookModel);

            // assert
            assertThat(result.getId()).isEqualTo(expected.getId());
            assertThat(result.getTitle()).isEqualTo(expected.getTitle());
            assertThat(result.getAuthor()).isEqualTo(expected.getAuthor());
            assertThat(result.getText()).isEqualTo(expected.getText());
        }
    }
}
