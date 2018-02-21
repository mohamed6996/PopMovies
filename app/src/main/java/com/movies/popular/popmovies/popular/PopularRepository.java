package com.movies.popular.popmovies.popular;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.util.Log;

import com.movies.popular.popmovies.AppExecutor;
import com.movies.popular.popmovies.model.MovieModel;

import android.arch.paging.PagedList.Config.Builder;

/**
 * Created by lenovo on 2/19/2018.
 */

public class PopularRepository {

    PopularDataSourceFactory popularDataSourceFactory = new PopularDataSourceFactory();
    LiveData<PagedList<MovieModel>> ret_list;

    @MainThread
    public LiveData<PagedList<MovieModel>> getPopularMovies() {

        PagedList.Config config = new Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(2)
                .build();


        try {
            ret_list = new LivePagedListBuilder(popularDataSourceFactory, config)
                    .setInitialLoadKey(1)
                    .setBackgroundThreadExecutor(AppExecutor.networkIO())
                    .build();
        } catch (Exception e) {
            Log.i("retrofit", e.getMessage());

        }

        return ret_list;

    }
}
