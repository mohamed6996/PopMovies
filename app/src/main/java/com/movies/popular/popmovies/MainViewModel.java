package com.movies.popular.popmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by lenovo on 2/19/2018.
 */

public class MainViewModel extends ViewModel {


    public LiveData<MovieList> getLiveData() {

        Repository repository = new Repository();
        return repository.getPopularMovies();

    }

}
