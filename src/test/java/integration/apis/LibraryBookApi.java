package integration.apis;

import integration.apis.models.TestLibraryBookLoanModel;
import integration.apis.models.TestNewLibraryBookLoanModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LibraryBookApi {
    @POST("library/books/loans")
    Call<TestLibraryBookLoanModel> loan(@Body TestNewLibraryBookLoanModel testNewLibraryBookLoanModel);
}
