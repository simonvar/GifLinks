package xyz.svapp.giflinks;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import xyz.svapp.giflinks.response.GifModel;
import xyz.svapp.giflinks.views.MainActivity;

/**
 * ViewHolder для гифок
 */

public class GifHolder extends RecyclerView.ViewHolder {

    private Context context;

    /**
     * Гифка
     * */
    private ImageView gifImageView;

    /**
     * ProgressBar загрузки гифки
     * */
    private ProgressBar progressBar;

    /**
     * ImageView при копировании ссылки
     * */
    private ImageView linkImageView;

    public GifHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        gifImageView = (ImageView) itemView.findViewById(R.id.item_gif_image);
        progressBar = (ProgressBar) itemView.findViewById(R.id.item_gif_progress);
        linkImageView = (ImageView) itemView.findViewById(R.id.item_gif_link);
    }

    public void bind(final GifModel gifModel){
        Glide.clear(gifImageView);
        gifImageView.setImageDrawable(null);
        Uri uri = Uri.parse(gifModel.getGifImage().getImageUrl().getFixedWidthUrl());

        progressBar.setVisibility(View.VISIBLE);

        int width = (int) convertDpToPixel(200, context);
        int height = (int) convertDpToPixel(gifModel.getGifImage().getImageUrl().getHeight(), context);

        Glide.with(context)
                .load(uri)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(width, height - 10)
                .listener(new RequestListener<Uri, GifDrawable>() {
                    @Override
                    public boolean onException(Exception e, Uri model, Target<GifDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Uri model, Target<GifDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(gifImageView);

        /*
         * Получение ссылки на гифку
         * */
        gifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linkImageView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.link_anim));
                linkImageView.setVisibility(View.VISIBLE);
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Gif Url", gifModel.getUrl());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Link successfully copied", Toast.LENGTH_SHORT).show();
                linkImageView.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     * Статичный метод, который переводит DP в PX
     * */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
