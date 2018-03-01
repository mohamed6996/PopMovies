package com.movies.popular.popmovies;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.crashlytics.android.Crashlytics;
import com.movies.popular.popmovies.adapters.ViewPagerAdapter;
import com.movies.popular.popmovies.fragments.FavouritsFragment;
import com.movies.popular.popmovies.fragments.PopularFragment;
import com.movies.popular.popmovies.fragments.TopRatedFragment;

import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    Toolbar toolbar;
    List<Fragment> fragmentList;
    List<String> titleList;
    FloatingSearchView floatingSearchView;
    Searchable searchable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        init();
        prepareFragments();


        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        floatingSearchView = findViewById(R.id.floating_search_view);
        floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
            }

            @Override
            public void onSearchAction(String query) {
                processSearch(query);
            }
        });

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_Layout);

    }

    private void processSearch(String query) {
        //  Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.view_pager + ":" + 0);
        Fragment page = viewPagerAdapter.getItem(viewPager.getCurrentItem()); // also work
        if (page != null) {
            switch (viewPager.getCurrentItem()) {
                case 0:
                    PopularFragment popularFragment = (PopularFragment) page;
                    searchable = (Searchable) popularFragment;
                    break;
                case 1:
                    TopRatedFragment topRatedFragment = (TopRatedFragment) page;
                    searchable = (Searchable) topRatedFragment;
                    break;

            }
            searchable.search(query);
        }
    }

    private void prepareFragments() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();

        addData(new PopularFragment(), "popular");
        addData(new TopRatedFragment(), "top rated");
        addData(new FavouritsFragment(), "favourits");
    }


    private void addData(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
