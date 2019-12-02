package com.github.isenng.libraryintegrationtesting.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.isenng.libraryintegrationtesting.dal.entities.Book;

public class BookModel {
    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("text")
    private String text;

    protected BookModel() { }

    public BookModel(long id, String title, String author, String text) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.text = text;
    }

    public BookModel(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.text = book.getText();
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
