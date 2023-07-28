package mada.android.datainterface.external;

import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.external.FirebaseToken;
import mada.android.models.users.User;
import mada.android.models.users.UserToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FirebaseTokenInterface {
    @POST("/firebase/subscribe-to-default-topic")
    Call<MessageResponse> subscribeToDefaultTopic(@Body FirebaseToken firebaseToken);
    @POST("/firebase/unsubscribe-to-default-topic")
    Call<MessageResponse> unsubscribeToDefaultTopic(@Body FirebaseToken firebaseToken);
}
