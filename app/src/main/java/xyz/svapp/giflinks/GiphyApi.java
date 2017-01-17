package xyz.svapp.giflinks;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import xyz.svapp.giflinks.response.GifModel;
import xyz.svapp.giflinks.response.Gifs;

/**
 * Интерфейс GiphyApi для выполнения запросов
 */

public interface GiphyApi {

    /**
     * Получения популярных гифок
     * @param apiKey ключ Api
     * */
    @GET("/v1/gifs/trending")
    Call<Gifs> getTrending(@Query("api_key") String apiKey);

    /**
     * Получение популярных запросов
     * @param apiKey ключ api
     * @param query строка поиска
     *
     * */
    @GET("/v1/gifs/search")
    Call<Gifs> getSearch(@Query("q") String query, @Query("api_key") String apiKey);

    /**
     * Получение популярных стикеров
     * @param apiKey ключ api
     * */
    @GET("/v1/stickers/trending")
    Call<Gifs> getStickers(@Query("api_key") String apiKey);

}
