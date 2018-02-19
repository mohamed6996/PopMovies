package com.movies.popular.popmovies;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.movies.popular.popmovies.adapters.RecyclerViewAdapter;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lenovo on 2/19/2018.
 */

public class Repository {

    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    Call<MovieList> call = apiInterface.getPopularMovies(Constants.API_KEY, 1);
    MutableLiveData<MovieList> ret_list = new MutableLiveData<>();


    public MutableLiveData<MovieList> getPopularMovies() {

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.isSuccessful()) {
                    ret_list.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.i("what", t.getMessage());
            }
        });

        return ret_list;
    }

}
