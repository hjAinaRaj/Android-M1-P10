package mada.android.services.external;

import mada.android.datainterface.external.FirebaseTokenInterface;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.DestinationList;
import mada.android.models.external.FirebaseToken;
import mada.android.tools.ws.RetrofitClientInstance;
import retrofit2.Call;

public class FirebaseTokenService {
    private FirebaseTokenInterface firebaseTokenInterface;

    public FirebaseTokenService() {
        if(firebaseTokenInterface == null){
            firebaseTokenInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(FirebaseTokenInterface.class);
        }
    }

    public Call<MessageResponse> subscribeToDefaultToken(FirebaseToken firebaseToken) throws Exception{
        Call<MessageResponse> call = firebaseTokenInterface.subscribeToDefaultTopic(firebaseToken);
        return call;
    }
}
