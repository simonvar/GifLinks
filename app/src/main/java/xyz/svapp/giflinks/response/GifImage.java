package xyz.svapp.giflinks.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Модель картинки гифки для парсинга через retrofit
 */
public class GifImage {

    @SerializedName("fixed_width")
    @Expose
    private GifParameters gifParameters;

    public GifParameters getImageUrl() {
        return gifParameters;
    }
}
