package com.movies.popular.popmovies;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

/**
 * Created by lenovo on 2/20/2018.
 */

public class MovieDataSourceFactory implements DataSource.Factory<Integer, MovieModel> {
    MutableLiveData<PageKeyedMovieDataSource> dataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, MovieModel> create() {
        PageKeyedMovieDataSource dataSource = new PageKeyedMovieDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }
}
