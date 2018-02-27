package com.movies.popular.popmovies.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularRepository;

/**
 * Created by lenovo on 2/27/2018.
 */

public class SearchViewModel extends ViewModel {

    LiveData<PagedList<MovieModel>> listLiveData;

    SearchRepository searchRepository;
    String query;

    public void setSearchQuery(String query) {
        this.query = query;
    }

    public LiveData<PagedList<MovieModel>> getLiveData() {

        // don`t make it singleton to support multiple search
        searchRepository = new SearchRepository();
        listLiveData = searchRepository.getSearchResults(this.query);
        return listLiveData;
    }


}
