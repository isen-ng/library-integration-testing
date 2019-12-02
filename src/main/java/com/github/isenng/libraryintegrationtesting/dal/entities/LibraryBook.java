package com.github.isenng.libraryintegrationtesting.dal.entities;

import javax.persistence.*;

@Entity
public class LibraryBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private Long dueDate;

    @OneToOne(targetEntity = Book.class, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    protected LibraryBook() {}

    public LibraryBook(long dueDate, long bookId) {
        this.dueDate = dueDate;
        this.book = new Book(bookId);
    }

    public LibraryBook(long id, long dueDate, long bookId) {
        this(dueDate, bookId);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Long getDueDate() {
        return dueDate;
    }

    public Book getBook() {
        return book;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }
}
