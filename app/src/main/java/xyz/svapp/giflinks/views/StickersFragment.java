package xyz.svapp.giflinks.views;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.svapp.giflinks.App;
import xyz.svapp.giflinks.GifsAdapter;
import xyz.svapp.giflinks.R;
import xyz.svapp.giflinks.response.GifModel;
import xyz.svapp.giflinks.response.Gifs;

/**
 * Фрагмент с популярными стикерами
 */
public class StickersFragment extends Fragment {

    private List<GifModel> gifs;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stickers, container, false);

        gifs = new ArrayList<>();

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.stickers_refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateGifs();
            }
        });

        int numColumns = getResources().getInteger(R.integer.column);

        recyclerView = (RecyclerView) view.findViewById(R.id.stickers_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numColumns, 1));
        recyclerView.setAdapter(new GifsAdapter(getActivity(), gifs));


        progressBar = (ProgressBar) view.findViewById(R.id.stickers_progress_bar);


        updateGifs();
        return view;
    }

    /**
     * Получение популярных стикеров
     */
    private void updateGifs(){
        progressBar.setVisibility(View.VISIBLE);
        App.getApi().getStickers(getResources().getString(R.string.api_key)).enqueue(new Callback<Gifs>() {
            @Override
            public void onResponse(Call<Gifs> call, Response<Gifs> response) {
                if (response.body() != null) {
                    gifs.clear();
                    gifs.addAll(response.body().getGifs());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                progressBar.setVisibility(View.INVISIBLE);
                refresh.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Gifs> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred during networking", Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });
    }
}
