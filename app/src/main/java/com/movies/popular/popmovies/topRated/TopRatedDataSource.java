package com.movies.popular.popmovies.topRated;

import android.support.annotation.NonNull;

import com.movies.popular.popmovies.Constants;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;
import com.movies.popular.popmovies.model.MovieList;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularDataSource;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by lenovo on 2/21/2018.
 */

public class TopRatedDataSource extends PopularDataSource {
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, MovieModel> callback) {
        try {
            Response<MovieList> response = apiInterface.getTopRatedMovies(Constants.API_KEY, 1).execute();
            if (response.isSuccessful()) {
                callback.onResult(response.body().getResults(), 1, 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {

        try {
            Response<MovieList> response = apiInterface.getTopRatedMovies(Constants.API_KEY, params.key).execute();
            if (response.isSuccessful()) {
                callback.onResult(response.body().getResults(), params.key + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, MovieModel> callback) {

    }
}
