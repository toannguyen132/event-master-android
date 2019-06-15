package project.com.eventmaster.network;

import android.content.res.Resources;

import project.com.eventmaster.Config;
import project.com.eventmaster.R;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = Config.API_URL; // "http://192.168.0.12:4000/";// "http://10.0.2.2:4000/";// "http://event.afullstackdev.com/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
