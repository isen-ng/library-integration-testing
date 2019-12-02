package integration;

import integration.apis.BookApi;
import integration.apis.models.TestBookModel;
import integration.apis.models.TestNewBookModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GlobalSetup.class)
class BookApiIT {
    private static BookApi bookApi;

    @BeforeAll
    static void setupClass() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        bookApi = retrofit.create(BookApi.class);
    }

    @Nested
    class Post {
        @Test
        void shouldFailWith400IfTitleIsEmpty() throws IOException {
            // arrange
            TestNewBookModel model = new TestNewBookModel("", "my_author", "my_text");

            // act
            Response<TestBookModel> result = bookApi.insertNewBook(model).execute();

            // assert
            assertThat(result.code()).isEqualTo(400);
        }

        @Test
        void shouldFailWith400IfAuthorIsEmpty() throws IOException {
            // arrange
            TestNewBookModel model = new TestNewBookModel("my_title", "", "my_text");

            // act
            Response<TestBookModel> result = bookApi.insertNewBook(model).execute();

            // assert
            assertThat(result.code()).isEqualTo(400);
        }

        @Test
        void shouldFailWith400IfTextIsEmpty() throws IOException {
            // arrange
            TestNewBookModel model = new TestNewBookModel("my_title", "my_author", "");

            // act
            Response<TestBookModel> result = bookApi.insertNewBook(model).execute();

            // assert
            assertThat(result.code()).isEqualTo(400);
        }

        @Test
        void shouldBeSuccessfulWith201IfDataIsValid() throws IOException {
            // arrange
            TestNewBookModel model = new TestNewBookModel("my_title", "my_author", "my_text");

            // act
            Response<TestBookModel> result = bookApi.insertNewBook(model).execute();

            // assert
            assertThat(result.code()).isEqualTo(201);

            assertThat(result.body().getTitle()).isEqualTo(model.getTitle());
            assertThat(result.body().getAuthor()).isEqualTo(model.getAuthor());
            assertThat(result.body().getText()).isEqualTo(model.getText());
        }
    }

    @Nested
    class Get {
        private TestBookModel created;

        @BeforeEach
        void setup() throws IOException {
            TestNewBookModel model = new TestNewBookModel("my_title", "my_author", "my_text");
            Response<TestBookModel> result = bookApi.insertNewBook(model).execute();

            assertThat(result.code()).isEqualTo(201);
            created = result.body();
        }

        @Test
        void shouldFailWith404IfBookDoesNotExist() throws IOException {
            // act
            Response<TestBookModel> result = bookApi.getBookById(-999).execute();

            // assert
            assertThat(result.code()).isEqualTo(404);
        }

        @Test
        void shouldBeSuccessfulWith200IfBookExists() throws IOException {
            // act
            Response<TestBookModel> result = bookApi.getBookById(created.getId()).execute();

            // assert
            assertThat(result.code()).isEqualTo(200);

            assertThat(result.body().getId()).isEqualTo(created.getId());
            assertThat(result.body().getTitle()).isEqualTo(created.getTitle());
            assertThat(result.body().getAuthor()).isEqualTo(created.getAuthor());
            assertThat(result.body().getText()).isEqualTo(created.getText());
        }
    }

}
