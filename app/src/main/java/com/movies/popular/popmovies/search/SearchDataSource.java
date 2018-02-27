package com.movies.popular.popmovies.search;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.movies.popular.popmovies.Constants;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;
import com.movies.popular.popmovies.model.MovieList;
import com.movies.popular.popmovies.model.MovieModel;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by lenovo on 2/27/2018.
 */

public class SearchDataSource extends PageKeyedDataSource<Integer, MovieModel> {
    String query;

    public SearchDataSource(String query) {
        this.query = query;
        Log.i("dataSource", "from data source  " + query);
    }

    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieModel> callback) {
        try {
            Response<MovieList> response = apiInterface.searchMovie(Constants.API_KEY, query, 1).execute();
            if (response.isSuccessful()) {
                callback.onResult(response.body().getResults(), 1, 2);
                Log.i("retrofit", "from initial" + response.body().getResults().size());

            } else {
                Log.i("retrofit", "from initial");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {

        try {
            Response<MovieList> response = apiInterface.searchMovie(Constants.API_KEY, query, params.key).execute();
            if (response.isSuccessful()) {
                callback.onResult(response.body().getResults(), params.key + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {
//
//        try {
//            Response<MovieList> response = apiInterface.getPopularMovies(Constants.API_KEY, params.key).execute();
//            if (response.isSuccessful()) {
//                int adjacentKey = 0;
//                if (params.key > 1)
//                    adjacentKey = params.key - 1;
//                else
//                    adjacentKey = 0 ;
//
//                    callback.onResult(response.body().getResults(), adjacentKey);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}