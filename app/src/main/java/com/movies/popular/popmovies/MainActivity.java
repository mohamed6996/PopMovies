/*
 * This project was submitted by Mohamed Ali as part of the Nanodegree At Udacity.
 *
 * As part of Udacity Honor code, your submissions must be your own work, hence
 * submitting this project as yours will cause you to break the Udacity Honor Code
 * and the suspension of your account.
 *
 * You could use the code as a reference, but if
 * you submit it, it's your own responsibility if you get expelled.
 *
 * Copyright (c) 2018 Mohamed Ali
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 *
 * MIT License
 *
 * Copyright (c) 2018 Mohamed Ali
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.movies.popular.popmovies;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.crashlytics.android.Crashlytics;
import com.movies.popular.popmovies.adapters.ViewPagerAdapter;
import com.movies.popular.popmovies.fragments.BaseFragment;
import com.movies.popular.popmovies.fragments.FavouritsFragment;
import com.movies.popular.popmovies.fragments.PopularFragment;
import com.movies.popular.popmovies.fragments.TopRatedFragment;
import com.movies.popular.popmovies.interfaces.Searchable;

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

    public static boolean mTWO_PANE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTWO_PANE = true;
        }

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
                if (query.isEmpty())
                    Toast.makeText(MainActivity.this, "Please specify your keyword!", Toast.LENGTH_SHORT).show();
                else
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
            BaseFragment baseFragment = (BaseFragment) page;
            searchable = baseFragment;
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
