package mada.android.datainterface;

import java.util.List;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.users.User;
import mada.android.models.users.UserToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserInterface {
    @GET("/users")
    Call<List<User>> get();

    @POST("/users/login")
    Call<UserToken> login(@Body User user);

    @POST("/users/signin")
    Call<MessageResponse> signin(@Body User user);
}
