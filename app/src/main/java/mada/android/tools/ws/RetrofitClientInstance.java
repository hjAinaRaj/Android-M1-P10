package mada.android.tools.ws;

import com.google.gson.Gson;

import java.io.IOException;

import mada.android.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Gson gson = new Gson();
    private static Retrofit retrofit = null;
    private static final String BASE_URL = BuildConfig.API_BASE_URL;
    public static ListObjectConverter<FilterItem> listObjectConverter = new ListObjectConverter<>(gson.getAdapter(FilterItem.class));
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(getTokenInterceptor());
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }

    private static Interceptor getTokenInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                // Modify the request to add the token header

                request = request.newBuilder()
                        .header("token",   "93949e3a6e7099311135262b9d17599b")
                        .build();

                return chain.proceed(request);
            }
        };
    }
}
