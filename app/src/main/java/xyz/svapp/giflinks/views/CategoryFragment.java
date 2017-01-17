package xyz.svapp.giflinks.views;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import xyz.svapp.giflinks.R;
import xyz.svapp.giflinks.views.MainActivity;


/**
 * Фрагмент категорий
 */
public class CategoryFragment extends Fragment {

    /**
     * Название папки ассетов, у которой лежат гифки категорий
     * */
    private static final String CATEGORY_FOLDER = "category";

    /**
     * Assets для загрузки гифок категорий
     * */
    private AssetManager assetManager;

    /**
     * Спиок категорий
     * */
    private List<String> listCat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assetManager = getActivity().getAssets();
        listCat = new ArrayList<>();
        listCat = loadCategory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CategoryAdapter(listCat));

        return view;
    }

    /**
     * Получение списка категрийй из assets
     *
     * */
    private List<String> loadCategory(){
        try {
            System.out.println(Arrays.asList(assetManager.list(CATEGORY_FOLDER)));
            return Arrays.asList(assetManager.list(CATEGORY_FOLDER));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    /**
     * ViewHolder для категорий
     * */
    private class CategoryHolder extends RecyclerView.ViewHolder{

        private ImageView gifImageView;
        private TextView textViewName;
        private View view;

        public CategoryHolder(View itemView) {
            super(itemView);
            view = itemView;
            gifImageView = (ImageView) itemView.findViewById(R.id.item_category_image);
            textViewName = (TextView) itemView.findViewById(R.id.item_category_text);
        }

        /**
         * Получение категорий роисходит следующим образом:
         * 1) Загрузка гифки категории из assets
         * 2) Название категории = название гифки
         * */
        private void bind(String filepath){
            Glide.with(getActivity())
                    .load(Uri.parse("file:///android_asset/" + CATEGORY_FOLDER + "/" + filepath))
                    .asGif()
                    .into(gifImageView);

            final String filename = filepath.replace(".gif", "");
            System.out.println(filepath);
            textViewName.setText(filename);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivity()).changeCategory(filename);
                }
            });
        }
    }

    /**
     * Adapter для категорий
     * */
    private class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder>{

        private List<String> categories;

        public CategoryAdapter(List<String> categories) {
            this.categories = categories;
        }

        @Override
        public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            return new CategoryHolder(v);
        }

        @Override
        public void onBindViewHolder(CategoryHolder holder, int position) {
            holder.bind(categories.get(position));
            System.out.println(categories.get(position));
        }

        @Override
        public int getItemCount() {
            if (categories == null){
                return 0;
            }
            return categories.size();
        }
    }

}
