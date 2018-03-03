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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.movies.popular.popmovies.DeatailActivity;
import com.movies.popular.popmovies.DetailFragment;
import com.movies.popular.popmovies.interfaces.ListItemClickListener;
import com.movies.popular.popmovies.MainActivity;
import com.movies.popular.popmovies.R;
import com.movies.popular.popmovies.interfaces.Searchable;
import com.movies.popular.popmovies.adapters.RecyclerViewAdapter;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.search.SearchViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment implements ListItemClickListener, Searchable {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<MovieModel> moviesList;
    SearchViewModel searchViewModel;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        recyclerView = view.findViewById(R.id.base_rec_view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }


    public void initRecyclerView() {
        adapter = new RecyclerViewAdapter(getContext(), this);
        int column_count = getResources().getInteger(R.integer.grid_column_count);
        GridLayoutManager manager = new GridLayoutManager(getContext(), column_count);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (adapter.getItemViewType(position)) {
                    case R.layout.movie_item_1:
                        if (column_count == 2) return 1;
                        else if (column_count == 3) return 1;
                        else return 1;
                    case R.layout.movie_item_2:
                        if (column_count == 2) return 2;
                        else if (column_count == 3) return 3;
                        else return 6;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void search(String query) {
        Toast.makeText(getContext(), "You searched for: " + query, Toast.LENGTH_SHORT).show();
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.setSearchQuery(query);

        try {
            searchViewModel.getLiveData().observe(this, new Observer<PagedList<MovieModel>>() {
                @Override
                public void onChanged(@Nullable PagedList<MovieModel> movieModels) {
                    if (movieModels != null) {
                        if (movieModels.size() != 0) {
                            adapter.setList(movieModels);
                            moviesList = movieModels;
                        }

                    }
                }
            });
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        MovieModel model = moviesList.get(clickedItemIndex);
        Bundle bundle = new Bundle();
        bundle.putString("movie_id",model.getId());

        if (MainActivity.mTWO_PANE){
            DetailFragment detailFragment = new DetailFragment();
            detailFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        }else {
            Intent intent = new Intent(getActivity(), DeatailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }



    }
}
