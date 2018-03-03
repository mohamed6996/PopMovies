package com.movies.popular.popmovies.topRated;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.util.Log;
import com.movies.popular.popmovies.utility.AppsExecutor;
import com.movies.popular.popmovies.model.MovieModel;

/**
 * Created by lenovo on 2/21/2018.
 */

public class TopRatedRepository {

    TopRatedFactory movieDataSourceFactory = new TopRatedFactory();
    LiveData<PagedList<MovieModel>> ret_list;

    @MainThread
    public LiveData<PagedList<MovieModel>> getPopularMovies() {

        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(2)
                .build();


        try {
            ret_list = new LivePagedListBuilder(movieDataSourceFactory, config)
                    .setInitialLoadKey(1)
                    .setBackgroundThreadExecutor(AppsExecutor.networkIO())
                    .build();
        } catch (Exception e) {
            Log.i("retrofit", e.getMessage());

        }

        return ret_list;

    }
}
