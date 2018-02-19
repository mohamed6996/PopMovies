package com.movies.popular.popmovies;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;

/**
 * Created by lenovo on 2/20/2018.
 */

public class PageKeyedMovieDataSource extends PageKeyedDataSource<Integer, MovieModel> {
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieModel> callback) {

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel> callback) {

    }
}
