package com.movies.popular.popmovies.topRated;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularRepository;

/**
 * Created by lenovo on 2/21/2018.
 */

public class TopRatedViewModel extends ViewModel {


    LiveData<PagedList<MovieModel>> listLiveData;
    TopRatedRepository topRatedRepository;

    public LiveData<PagedList<MovieModel>> getLiveData() {
        if (topRatedRepository == null) {
            topRatedRepository = new TopRatedRepository();
            listLiveData = topRatedRepository.getPopularMovies();

        }
        return listLiveData;
    }

}