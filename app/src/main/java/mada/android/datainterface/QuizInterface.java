package mada.android.datainterface;

import java.util.Map;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.models.destination.QuizList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface QuizInterface {
    @GET("/quiz")
    Call<QuizList> get();


    @GET("destination/{id}")
    Call<QuizList> getItemById(@Path("id") String itemId);

}
