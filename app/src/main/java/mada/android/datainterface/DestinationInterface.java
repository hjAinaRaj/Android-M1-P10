package mada.android.datainterface;

import java.util.List;
import java.util.Map;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.tools.ws.FilterItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface DestinationInterface {
    @GET("/destination")
    Call<DestinationList> get();

    @GET("/destination")
    Call<DestinationList> getForConnectedUser(@QueryMap Map<String, String> filters);

    @GET("destination/{id}")
    Call<Destination> getItemById(@Path("id") String itemId);

    @POST("/destination")
    Call<MessageResponse> post(@Body Destination destination);
}
