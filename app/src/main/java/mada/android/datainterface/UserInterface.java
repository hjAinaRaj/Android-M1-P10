package mada.android.datainterface;

import java.util.List;

import mada.android.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("/users")
    Call<List<User>> get();

    @POST("/login")
    Call<User> login(@Body User retroPhoto);

    @POST("/signin")
    Call<User> signin(@Body User retroPhoto);
}
