package com.github.isenng.libraryintegrationtesting.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.isenng.libraryintegrationtesting.dal.entities.LibraryBook;

public class LibraryBookLoanModel {
    @JsonProperty("id")
    private long id;

    @JsonProperty("due_date")
    private long dueDate;

    @JsonProperty("book_id")
    private long bookId;

    public LibraryBookLoanModel(long id, long dueDate, long bookId) {
        this.id = id;
        this.dueDate = dueDate;
        this.bookId = bookId;
    }

    public LibraryBookLoanModel(LibraryBook libraryBook) {
        this.id = libraryBook.getId();
        this.dueDate = libraryBook.getDueDate();
        this.bookId = libraryBook.getBook().getId();
    }

    public long getId() {
        return id;
    }

    public long getDueDate() {
        return dueDate;
    }

    public long getBookId() {
        return bookId;
    }
}
