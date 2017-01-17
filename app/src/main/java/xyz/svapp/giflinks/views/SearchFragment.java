package xyz.svapp.giflinks.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
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
 * Фрагмент поиска
 */
public class SearchFragment extends Fragment {

    /**
     * Список гифок
     **/
    private List<GifModel> gifs;

    /**
     * Если ничего не найдено показывается этот TextView
     **/
    private TextView textViewNothing;

    /**
     * TextView, в котором показан запрос
     **/
    private TextView searchTextView;

    /**
     * Строка запроса
     * */
    private String qry;

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        gifs = new ArrayList<>();

        refresh = (SwipeRefreshLayout) view.findViewById(R.id.search_refresh);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (qry != null) {
                    updateItems(qry);
                } else {
                    refresh.setRefreshing(false);
                }
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(getResources().getInteger(R.integer.column), 1));
        recyclerView.setAdapter(new GifsAdapter(getActivity(), gifs));

        progressBar = (ProgressBar) view.findViewById(R.id.search_progress);
        progressBar.setVisibility(View.INVISIBLE);
        textViewNothing = (TextView) view.findViewById(R.id.search_text_nothing);

        searchTextView = (TextView) view.findViewById(R.id.search_text_view);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                qry = query;
                updateItems(qry);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Получение результатов поискового запроса
     * */
    private void updateItems(final String query){
        App.getApi().getSearch(query, getResources().getString(R.string.api_key)).enqueue(new Callback<Gifs>() {
            @Override
            public void onResponse(Call<Gifs> call, Response<Gifs> response) {
                searchTextView.setText("");
                if (response.body() != null) {
                    searchTextView.setText(getString(R.string.gifs_search, query));
                    gifs.clear();
                    gifs.addAll(response.body().getGifs());
                    recyclerView.getAdapter().notifyDataSetChanged();
                    if (gifs.size() > 0){
                        textViewNothing.setVisibility(View.INVISIBLE);
                    } else {
                        textViewNothing.setVisibility(View.VISIBLE);
                    }
                }
                progressBar.setVisibility(View.INVISIBLE);
                refresh.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<Gifs> call, Throwable t) {
                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
                refresh.setRefreshing(false);
            }
        });
    }
}
