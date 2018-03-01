package com.movies.popular.popmovies.fragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.movies.popular.popmovies.DeatailActivity;
import com.movies.popular.popmovies.ListItemClickListener;
import com.movies.popular.popmovies.R;
import com.movies.popular.popmovies.adapters.RecyclerViewAdapter;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.room.MovieViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritsFragment extends Fragment implements ListItemClickListener {
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    MovieViewModel movieViewModel;
    List<MovieModel> moviesList;

    public FavouritsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourits, container, false);
        recyclerView = view.findViewById(R.id.fav_rec_view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

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


    private void initRecyclerView() {
        adapter = new RecyclerViewAdapter(getContext(), this);

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


    @Override
    public void onListItemClick(int clickedItemIndex) {
        MovieModel model = moviesList.get(clickedItemIndex);
        Intent intent = new Intent(getActivity(), DeatailActivity.class);
        intent.putExtra("movie_id", model.getId());
        startActivity(intent);
    }
}
