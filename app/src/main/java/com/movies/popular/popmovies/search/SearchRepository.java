package com.movies.popular.popmovies.search;

import android.arch.lifecycle.LiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.util.Log;

import com.movies.popular.popmovies.AppExecutor;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularDataSourceFactory;

/**
 * Created by lenovo on 2/27/2018.
 */

public class SearchRepository {

    SearchDataSourceFactory searchDataSourceFactory = new SearchDataSourceFactory();
    LiveData<PagedList<MovieModel>> ret_list;

    @MainThread
    public LiveData<PagedList<MovieModel>> getSearchResults(String query) {

        Log.i("retrofit", "from search repo  " + query);

        searchDataSourceFactory.setSearchQuery(query);


        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .setPrefetchDistance(2)
                .build();


        try {
            ret_list = new LivePagedListBuilder(searchDataSourceFactory, config)
                    .setInitialLoadKey(1)
                    .setBackgroundThreadExecutor(AppExecutor.networkIO())
                    .build();

            Log.i("retrofit", "ret_list" + ret_list.getValue().size());

        } catch (Exception e) {
            Log.i("retrofit", e.getMessage());

        }

        return ret_list;

    }
}
