package mada.android.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mada.android.datainterface.DestinationInterface;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.tools.ws.FilterItem;
import mada.android.tools.ws.RetrofitClientInstance;
import retrofit2.Call;

public class DestinationService {
    private DestinationInterface destinationInterface = null;

    public DestinationService() {
        if(destinationInterface == null){
            destinationInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(DestinationInterface.class);
        }
    }

    public Call<DestinationList> get() throws Exception{
        Call<DestinationList> call = destinationInterface.get();
        return call;
    }

    public Call<DestinationList> getForConnectedUser(String userId) throws Exception{
        FilterItem favoritesOnly = new FilterItem("isFavorite", "=", false, "boolean");
        List<FilterItem> filter = new ArrayList<FilterItem>() {{
            add(favoritesOnly);
            add(favoritesOnly);
        }};

        Call<DestinationList> call = destinationInterface.getForConnectedUser(userId, FilterItem.generateMap(filter));
        return call;
    }
    public Call<Destination> get(String id) throws Exception{
        Call<Destination> call = destinationInterface.getItemById(id);
        return call;
    }

    public Call<MessageResponse> post(Destination destination) throws Exception{
        Call<MessageResponse> call = destinationInterface.post(destination);
        return call;
    }
}
