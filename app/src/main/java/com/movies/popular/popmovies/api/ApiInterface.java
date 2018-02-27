package com.movies.popular.popmovies.api;

import com.movies.popular.popmovies.model.MovieList;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.model.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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


    @GET("movie/top_rated")
    Call<MovieList> getTopRatedMovies(

            @Query("api_key") String api_key,
            @Query("page") int page
    );


    @GET("movie/{movie_id}")
    Call<MovieModel> getMovieDetails(
            @Path("movie_id") String movieID,
            @Query("api_key") String api_key
    );

    @GET("movie/{movie_id}/videos")
    Call<TrailerList> getYoutubeKey(
            @Path("movie_id") String movieID,
            @Query("api_key") String api_key
    );


    @GET("movie/{movie_id}/reviews")
    Call<TrailerList> getReviews(
            @Path("movie_id") String movieID,
            @Query("api_key") String api_key
    );


    @GET("search/movie")
    Call<MovieList> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query,
            @Query("page") int page
    );

}
