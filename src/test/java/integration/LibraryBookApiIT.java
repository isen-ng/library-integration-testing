package integration;

import integration.apis.BookApi;
import integration.apis.LibraryBookApi;
import integration.apis.models.TestBookModel;
import integration.apis.models.TestLibraryBookLoanModel;
import integration.apis.models.TestNewBookModel;
import integration.apis.models.TestNewLibraryBookLoanModel;
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
public class LibraryBookApiIT {
    private static BookApi bookApi;
    private static LibraryBookApi libraryBookApi;

    @BeforeAll
    static void setupClass() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        bookApi = retrofit.create(BookApi.class);
        libraryBookApi = retrofit.create(LibraryBookApi.class);
    }

    @Nested
    class Post {
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
            Response<TestLibraryBookLoanModel> result = libraryBookApi
                    .loan(new TestNewLibraryBookLoanModel(-999))
                    .execute();

            // assert
            assertThat(result.code()).isEqualTo(404);
        }

        @Test
        void shouldFailWith400IfBookIsAlreadyCheckedOut() throws IOException {
            // arrange
            Response<TestLibraryBookLoanModel> loan = libraryBookApi
                    .loan(new TestNewLibraryBookLoanModel(created.getId()))
                    .execute();

            assertThat(loan.code()).isEqualTo(201);

            // act
            Response<TestLibraryBookLoanModel> result = libraryBookApi
                    .loan(new TestNewLibraryBookLoanModel(created.getId()))
                    .execute();

            // assert
            assertThat(result.code()).isEqualTo(400);
        }

        @Test
        void shouldBeSuccessfulWith201IfInputsAreAllValid() throws IOException {
            // act
            Response<TestLibraryBookLoanModel> result = libraryBookApi
                    .loan(new TestNewLibraryBookLoanModel(created.getId()))
                    .execute();

            // assert
            assertThat(result.code()).isEqualTo(201);
        }
    }
}
