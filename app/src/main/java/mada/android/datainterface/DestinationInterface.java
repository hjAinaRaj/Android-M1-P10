package mada.android.datainterface;

import java.util.List;

import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DestinationInterface {
    @GET("/destination")
    Call<DestinationList> get();
}
