package mada.android.services;

import mada.android.datainterface.UserInterface;
import mada.android.models.users.User;
import mada.android.models.users.UserToken;
import mada.android.tools.ws.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Response;

public class UserService {
    private UserInterface userInterface = null;

    public UserService() {
        if(userInterface == null){
            userInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(UserInterface.class);
        }
    }

    public Call<UserToken> login(User user) throws Exception{
        Call<UserToken> call = userInterface.login(user);
        return call;
    }
}
