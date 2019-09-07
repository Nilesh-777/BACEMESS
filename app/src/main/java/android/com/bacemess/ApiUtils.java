package android.com.bacemess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Nilesh on 2/2/2018.
 */

public class ApiUtils {
    private static final String SERVER_PATH = "http://prasadam.epizy.com/";

    private static Gson gson = new GsonBuilder().create();
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor);
    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();



    public static Retrofit retroEntity() {


        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;

    }
}
