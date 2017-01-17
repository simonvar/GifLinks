package xyz.svapp.giflinks.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Модель параметров картинки гифки для парсинга через retrofit
 */
public class GifParameters {

    @SerializedName("url")
    @Expose
    private String fixedWidthUrl;

    @SerializedName("width")
    @Expose
    private int width;

    @SerializedName("height")
    @Expose
    private int height;

    public String getFixedWidthUrl() {
        return fixedWidthUrl;
    }

    public int getHeight() {
        return height;
    }
}
