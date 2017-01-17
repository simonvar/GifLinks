package xyz.svapp.giflinks;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Подключение GiphyApi и Retrofit при создании экземпляра приложения
 */

public class App extends Application {

    private static GiphyApi giphyApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.giphy.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        giphyApi = retrofit.create(GiphyApi.class);
    }

    public static GiphyApi getApi(){
        return giphyApi;
    }
}
