package com.movies.popular.popmovies.popular;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.movies.popular.popmovies.model.MovieModel;

/**
 * Created by lenovo on 2/19/2018.
 */

public class PopularViewModel extends ViewModel {


    LiveData<PagedList<MovieModel>> listLiveData;

    PopularRepository popularRepository;


    public LiveData<PagedList<MovieModel>> getLiveData() {
        if (popularRepository == null) {
            popularRepository = new PopularRepository();
            listLiveData = popularRepository.getPopularMovies();
        }
        return listLiveData;
    }

}
