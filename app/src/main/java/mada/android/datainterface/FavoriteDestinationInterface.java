package mada.android.datainterface;

import java.util.Map;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.models.destination.FavoriteDestination;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface FavoriteDestinationInterface {
    @POST("/destination/favorites")
    Call<MessageResponse> create(@Body FavoriteDestination destination);

    @DELETE("/destination/favorites/{destinationId}")
    Call<MessageResponse> delete(@Path("destinationId") String destinationId);

}
