package mada.android.tools.ws;

import com.google.gson.Gson;

import mada.android.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Gson gson = new Gson();
    private static Retrofit retrofit = null;
    private static final String BASE_URL = BuildConfig.API_BASE_URL;
    public static ListObjectConverter<FilterItem> listObjectConverter = new ListObjectConverter<>(gson.getAdapter(FilterItem.class));
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
