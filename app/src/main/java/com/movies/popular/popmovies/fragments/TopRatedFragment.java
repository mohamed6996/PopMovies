package com.movies.popular.popmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.topRated.TopRatedViewModel;
import com.movies.popular.popmovies.utility.NetworkUtility;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopRatedFragment extends BaseFragment {

    TopRatedViewModel topRatedViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        if (NetworkUtility.getNetworkState(getContext()).isNetworkAvailable()) {
            topRatedViewModel = ViewModelProviders.of(this).get(TopRatedViewModel.class);
            try {
                topRatedViewModel.getLiveData().observe(this, new Observer<PagedList<MovieModel>>() {
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
        } else {
            Toast.makeText(getContext(), "Check your network connection!", Toast.LENGTH_SHORT).show();
        }

    }


}
