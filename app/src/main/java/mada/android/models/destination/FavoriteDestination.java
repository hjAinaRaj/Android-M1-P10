package mada.android.models.destination;

import com.google.gson.annotations.SerializedName;

public class FavoriteDestination {

    @SerializedName("destinationId")
    private String destinationId;

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }
    public FavoriteDestination(){}
    public FavoriteDestination(String destinationId) {
        this.destinationId = destinationId;
    }
}
