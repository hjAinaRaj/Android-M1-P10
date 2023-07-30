package mada.android.tools.ws;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

public class ListObjectConverter <T> extends TypeAdapter<List<T>> {
    private final TypeAdapter<T> adapter;
    private final Gson gson = new Gson();

    public ListObjectConverter(TypeAdapter<T> adapter) {
        this.adapter = adapter;
    }

    @Override
    public void write(JsonWriter out, List<T> value) throws IOException {
        throw new UnsupportedOperationException("ListObjectConverter serialization is not supported.");
    }

    @Override
    public List<T> read(JsonReader in) throws IOException {
        in.beginArray();
        List<T> list = gson.fromJson(in, List.class);
        in.endArray();
        return list;
    }

    public String toString(List<T> value) {
        return gson.toJson(value);
    }
    public String toString(T value) {
        return gson.toJson(value);
    }
}
