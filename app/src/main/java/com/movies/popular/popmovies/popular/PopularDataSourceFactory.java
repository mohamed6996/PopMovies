package com.movies.popular.popmovies.popular;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.movies.popular.popmovies.model.MovieModel;

/**
 * Created by lenovo on 2/20/2018.
 */

public class PopularDataSourceFactory implements DataSource.Factory<Integer, MovieModel> {
    MutableLiveData<PopularDataSource> dataSourceMutableLiveData = new MutableLiveData<>();


    @Override
    public DataSource<Integer, MovieModel> create() {
        PopularDataSource dataSource = new PopularDataSource();
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }
}
