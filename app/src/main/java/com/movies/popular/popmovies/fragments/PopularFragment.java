package com.movies.popular.popmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movies.popular.popmovies.MainViewModel;
import com.movies.popular.popmovies.MovieList;
import com.movies.popular.popmovies.MovieModel;
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

        try {
            mainViewModel.getLiveData().observe(this, new Observer<PagedList<MovieModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<MovieModel> movieModels) {
                    if (movieModels != null)
                        adapter.setList(movieModels);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }

    private void initRecyclerView() {
        adapter = new RecyclerViewAdapter(getContext());

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case R.layout.movie_item_1:
                        return 1;
                    case R.layout.movie_item_2:
                        return 2;
                    default:
                        return 1;
                }



            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

}
