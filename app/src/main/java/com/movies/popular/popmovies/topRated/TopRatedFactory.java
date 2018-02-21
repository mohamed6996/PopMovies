package com.movies.popular.popmovies.topRated;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.movies.popular.popmovies.model.MovieModel;

/**
 * Created by lenovo on 2/21/2018.
 */

public class TopRatedFactory implements DataSource.Factory<Integer, MovieModel> {

    MutableLiveData<TopRatedDataSource> dataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, MovieModel> create() {
        TopRatedDataSource dataSource = new TopRatedDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }
}
