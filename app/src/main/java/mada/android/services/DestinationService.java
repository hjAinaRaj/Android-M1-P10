package mada.android.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import mada.android.datainterface.DestinationInterface;
import mada.android.datainterface.FavoriteDestinationInterface;
import mada.android.models.defaultResponses.MessageResponse;
import mada.android.models.destination.Destination;
import mada.android.models.destination.DestinationList;
import mada.android.models.destination.FavoriteDestination;
import mada.android.tools.ws.FilterItem;
import mada.android.tools.ws.RetrofitClientInstance;
import retrofit2.Call;

public class DestinationService {
    private DestinationInterface destinationInterface = null;
    private FavoriteDestinationInterface favoriteDestinationInterface = null;
    public DestinationService() {
        if(destinationInterface == null){
            destinationInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(DestinationInterface.class);
        }
        if(favoriteDestinationInterface == null){
            favoriteDestinationInterface = RetrofitClientInstance.getRetrofitInstance()
                    .create(FavoriteDestinationInterface.class);
        }
    }

    public Call<DestinationList> get() throws Exception{
        Call<DestinationList> call = destinationInterface.get();
        return call;
    }

    public Call<DestinationList> getForConnectedUser( List<FilterItem> filter) throws Exception{

        Call<DestinationList> call = destinationInterface.getForConnectedUser(FilterItem.generateMap(filter));
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

    public Call<MessageResponse> addFavorite(String destinationId) throws Exception{
        Call<MessageResponse> call = favoriteDestinationInterface.create(new FavoriteDestination(destinationId));
        return call;
    }

    public Call<MessageResponse> removeFavorite(String destinationId) throws Exception{
        Call<MessageResponse> call = favoriteDestinationInterface.delete(destinationId);
        return call;
    }
}
