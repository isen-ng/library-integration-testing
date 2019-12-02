package com.github.isenng.libraryintegrationtesting.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewLibraryBookLoanModel {
    @JsonProperty("book_id")
    private long bookId;

    protected NewLibraryBookLoanModel() { }

    public NewLibraryBookLoanModel(long bookId) {
        this.bookId = bookId;
    }

    public long getBookId() {
        return bookId;
    }
}
