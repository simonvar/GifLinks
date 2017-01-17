package xyz.svapp.giflinks.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Модель гифки для парсинга через retrofit
 */
public class GifModel implements Serializable{

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("import_datetime")
    @Expose
    private String date;

    @SerializedName("images")
    @Expose
    private GifImage gifImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public GifImage getGifImage() {
        return gifImage;
    }

}
