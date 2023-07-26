package mada.android.models.destination;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import mada.android.models.defaultResponses.MetaResponse;

public class DestinationList {
    @SerializedName("data")
    private ArrayList<Destination> data;
    @SerializedName("meta")
    private MetaResponse meta;

    public DestinationList() {
    }

    public DestinationList(ArrayList<Destination> data, MetaResponse meta) {
        this.data = data;
        this.meta = meta;
    }

    public ArrayList<Destination> getData() {
        return data;
    }

    public void setData(ArrayList<Destination> data) {
        this.data = data;
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }
}
