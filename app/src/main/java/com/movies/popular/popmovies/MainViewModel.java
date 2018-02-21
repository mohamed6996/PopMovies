package com.movies.popular.popmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

/**
 * Created by lenovo on 2/19/2018.
 */

public class MainViewModel extends ViewModel {


    LiveData<PagedList<MovieModel>> listLiveData;
    public LiveData<PagedList<MovieModel>> getLiveData() {
        listLiveData = new Repository().getPopularMovies();
        return listLiveData ;
    }
}
