package integration.apis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestLibraryBookLoanModel {
    @JsonProperty("id")
    private long id;

    @JsonProperty("due_date")
    private long dueDate;

    @JsonProperty("book_id")
    private long bookId;

    protected TestLibraryBookLoanModel() { }

    public TestLibraryBookLoanModel(long id, long dueDate, long bookId) {
        this.id = id;
        this.dueDate = dueDate;
        this.bookId = bookId;
    }

    public long getId() {
        return id;
    }

    public long getDueDate() {
        return dueDate;
    }

    public long getBook_id() {
        return bookId;
    }
}
