package com.movies.popular.popmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.room.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritsFragment extends BaseFragment {

    MovieViewModel movieViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setContext(getContext());

        try {
            movieViewModel.getAllMovies().observe(this, new Observer<PagedList<MovieModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<MovieModel> movieModels) {
                    if (movieModels != null) {
                        adapter.setList(movieModels);
                        moviesList = movieModels;
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void search(String query) {
        // do nothing
    }

}
