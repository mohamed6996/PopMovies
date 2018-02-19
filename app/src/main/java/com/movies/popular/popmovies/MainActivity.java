package com.movies.popular.popmovies;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.movies.popular.popmovies.adapters.ViewPagerAdapter;
import com.movies.popular.popmovies.fragments.FavouritsFragment;
import com.movies.popular.popmovies.fragments.PopularFragment;
import com.movies.popular.popmovies.fragments.TopRatedFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    TabLayout tabLayout;
    Toolbar toolbar;

    List<Fragment> fragmentList;
    List<String> titleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        prepareFragments();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Splash");
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_Layout);
    }

    private void prepareFragments() {
        fragmentList = new ArrayList<>();
        titleList = new ArrayList<>();

        adddata(new PopularFragment(), "popular");
        adddata(new TopRatedFragment(), "top rated");
        adddata(new FavouritsFragment(), "favourits");
    }


    private void adddata(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }
}
