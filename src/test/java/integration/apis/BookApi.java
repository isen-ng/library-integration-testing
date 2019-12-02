package integration.apis;

import integration.apis.models.TestBookModel;
import integration.apis.models.TestNewBookModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookApi {
    @GET("books/{id}")
    Call<TestBookModel> getBookById(@Path("id") long id);

    @POST("books")
    Call<TestBookModel> insertNewBook(@Body TestNewBookModel testNewBookModel);
}
