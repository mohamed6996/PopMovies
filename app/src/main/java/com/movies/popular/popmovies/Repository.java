package com.movies.popular.popmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.MainThread;
import android.util.Log;

import com.movies.popular.popmovies.adapters.RecyclerViewAdapter;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.arch.paging.PagedList.Config.Builder;

/**
 * Created by lenovo on 2/19/2018.
 */

public class Repository {

    MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory();
    LiveData<PagedList<MovieModel>> ret_list;

    @MainThread
    public LiveData<PagedList<MovieModel>> getPopularMovies() {
        PagedList.Config config = new Builder()
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();

        try {
            ret_list = new LivePagedListBuilder(movieDataSourceFactory, config)
                    .setInitialLoadKey(1)
                    .setBackgroundThreadExecutor(AppExecutor.networkIO())
                    .build();
        } catch (Exception e) {
            Log.i("retrofit", e.getMessage());

        }


        return ret_list;

    }
}
