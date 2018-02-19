package com.movies.popular.popmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.movies.popular.popmovies.MainViewModel;
import com.movies.popular.popmovies.MovieList;
import com.movies.popular.popmovies.R;
import com.movies.popular.popmovies.adapters.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    MainViewModel mainViewModel;

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        recyclerView = view.findViewById(R.id.pop_rec_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRecyclerView();
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getLiveData().observe(this, new Observer<MovieList>() {
            @Override
            public void onChanged(@Nullable MovieList movieList) {
                if (movieList != null) {
                    adapter = new RecyclerViewAdapter(movieList);
                }
            }
        });


    }

    private void initRecyclerView() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
    }

}
