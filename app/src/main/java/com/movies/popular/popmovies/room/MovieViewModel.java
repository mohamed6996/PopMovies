package com.movies.popular.popmovies.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.movies.popular.popmovies.model.MovieModel;

import java.util.List;

/**
 * Created by lenovo on 2/28/2018.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;
    private LiveData<PagedList<MovieModel>> allMovies;

    public MovieViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        allMovies = mRepository.getAllFavorites();
    }

    public LiveData<PagedList<MovieModel>> getAllMovies() {
        return allMovies;
    }

    public void insert(MovieEntity model) { mRepository.insert(model); }
}
