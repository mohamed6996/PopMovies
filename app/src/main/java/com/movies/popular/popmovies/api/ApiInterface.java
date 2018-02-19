package com.movies.popular.popmovies.api;

import com.movies.popular.popmovies.MovieList;
import com.movies.popular.popmovies.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2/19/2018.
 */

public interface ApiInterface {

    @GET("movie/popular")
    Call<MovieList> getPopularMovies(

            @Query("api_key") String api_key,
            @Query("page") int page
    );


//    @GET("movie/popular?api_key=1a618051961d7a730414257885f0d9d3&language=en-US&page=1")
//    Call<MovieList> getPopularMovies()

}
