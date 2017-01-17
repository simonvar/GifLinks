package xyz.svapp.giflinks.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Модель списка гифок для парсинга через retrofit
 */
public class Gifs {

    @SerializedName("data")
    @Expose
    private List<GifModel> gifs;

    public List<GifModel> getGifs() {
        return gifs;
    }
}
