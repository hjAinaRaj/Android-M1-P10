package mada.android.datainterface;

import java.util.List;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface DestinationInterface {
    @GET("/destination")
    Call<DestinationList> get();

    @GET("destination/{id}")
    Call<Destination> getItemById(@Path("id") String itemId);

    @POST("/destination")
    Call<MessageResponse> post(@Body Destination destination);
}
