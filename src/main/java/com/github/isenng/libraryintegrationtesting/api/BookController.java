package com.github.isenng.libraryintegrationtesting.api;

import com.github.isenng.libraryintegrationtesting.api.internal.PreHttpConditions;
import com.github.isenng.libraryintegrationtesting.api.models.BookModel;
import com.github.isenng.libraryintegrationtesting.api.models.NewBookModel;
import com.github.isenng.libraryintegrationtesting.dal.entities.Book;
import com.github.isenng.libraryintegrationtesting.service.books.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "/{id}")
    public BookModel get(@PathVariable("id") long id) {
        Optional<Book> book = bookService.Get(id);
        return new BookModel(book.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookModel post(@RequestBody NewBookModel newBook) {
        PreHttpConditions.checkNotNullOrEmpty(newBook.getTitle(), "title");
        PreHttpConditions.checkNotNullOrEmpty(newBook.getAuthor(), "author");
        PreHttpConditions.checkNotNullOrEmpty(newBook.getText(), "text");

        return new BookModel(
                bookService.Insert(newBook.getTitle(), newBook.getAuthor(), newBook.getText()));
    }
}
