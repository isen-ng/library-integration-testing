package integration.apis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestNewLibraryBookLoanModel {
    @JsonProperty("book_id")
    private long bookId;

    protected TestNewLibraryBookLoanModel() { }

    public TestNewLibraryBookLoanModel(long bookId) {
        this.bookId = bookId;
    }

    public long getBook_id() {
        return bookId;
    }
}
