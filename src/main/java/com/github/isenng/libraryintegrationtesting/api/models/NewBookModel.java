package com.github.isenng.libraryintegrationtesting.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewBookModel {
    @JsonProperty("title")
    private String title;

    @JsonProperty("author")
    private String author;

    @JsonProperty("text")
    private String text;

    protected NewBookModel() { }

    public NewBookModel(String title, String author, String text) {
        this.title = title;
        this.author = author;
        this.text = text;
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
