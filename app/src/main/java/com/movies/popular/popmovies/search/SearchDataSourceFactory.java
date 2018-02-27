package com.movies.popular.popmovies.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularDataSource;

/**
 * Created by lenovo on 2/27/2018.
 */

public class SearchDataSourceFactory implements DataSource.Factory<Integer, MovieModel> {
    MutableLiveData<SearchDataSource> dataSourceMutableLiveData = new MutableLiveData<>();
    String query;

    void setSearchQuery(String query) {
        this.query = query;
    }

    @Override
    public DataSource<Integer, MovieModel> create() {
        SearchDataSource dataSource = new SearchDataSource(this.query);
        dataSourceMutableLiveData.postValue(dataSource);
        return dataSource;
    }
}