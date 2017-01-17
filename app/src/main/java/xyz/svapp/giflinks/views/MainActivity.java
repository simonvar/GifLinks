package xyz.svapp.giflinks.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import xyz.svapp.giflinks.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private boolean isCategory = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_trending);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, new TrendingFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isCategory){
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().replace(R.id.fragment_container, new CategoryFragment()).commit();
            isCategory = false;
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Смена фрагмента на фрагмент категорий
     * */
    public void changeCategory(String query){
        Fragment fragment = new CategorySearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CategorySearchFragment.EXTRA_QUERY, query);
        fragment.setArguments(bundle);
        isCategory = true;
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }


    /**
     * Смена фрагмента по выбору элемента меню
     * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = new TrendingFragment();
        String title = getResources().getString(R.string.trending);

        if (id == R.id.nav_trending) {
            fragment = new TrendingFragment();
            title = getResources().getString(R.string.trending);
        } else if (id == R.id.nav_category) {
            fragment = new CategoryFragment();
            title = getResources().getString(R.string.category);
        } else if (id == R.id.nav_search) {
            fragment = new SearchFragment();
            title = getResources().getString(R.string.keyword_search);
        } else if (id == R.id.nav_stickers) {
            fragment = new StickersFragment();
            title = getResources().getString(R.string.stickers);
        }

        isCategory = false;
        toolbar.setTitle(title);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
