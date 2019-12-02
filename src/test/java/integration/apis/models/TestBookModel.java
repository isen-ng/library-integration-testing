package integration.apis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestBookModel extends TestNewBookModel {
    @JsonProperty("id")
    private long id;

    protected TestBookModel() { super(); }

    public TestBookModel(long id, String title, String author, String text) {
        super(title, author, text);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
