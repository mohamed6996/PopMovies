package com.movies.popular.popmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.movies.popular.popmovies.adapters.ReviewAdapter;
import com.movies.popular.popmovies.adapters.TrailerAdapter;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.model.TrailerList;
import com.movies.popular.popmovies.model.TrailerModel;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    MovieModel model;
    CollapsingToolbarLayout collapsingToolbar;
    ImageView backDrop, poster;
    TextView title_tv, synopsis_tv, releaseDate_tv, duration_tv,
            vote_avg_tv, vote_count_tv, pop_tv, lang_tv, overview_tv;

    Button getTrailers_btn, getReview_btn;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;

    RecyclerView sheetRecyclerView;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton fab;
    BottomSheetBehavior behavior;

    TrailerList trailerList, reviewList;

    //  ListView trailerListView;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        collapsingToolbar = view.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(model.getTitle());
        backDrop = view.findViewById(R.id.back_drop);

        poster = view.findViewById(R.id.detail_poster_iv);
        title_tv = view.findViewById(R.id.detail_title_tv);
        synopsis_tv = view.findViewById(R.id.detail_synopsis_tv);
        releaseDate_tv = view.findViewById(R.id.detail_release_date_tv);
        duration_tv = view.findViewById(R.id.detail_duration);

        vote_avg_tv = view.findViewById(R.id.detail_vote_avg);
        vote_count_tv = view.findViewById(R.id.detail_vote_count);
        pop_tv = view.findViewById(R.id.detail_pop);
        lang_tv = view.findViewById(R.id.detail_lang);
        overview_tv = view.findViewById(R.id.detail_overview);


        //   synopsis_tv.setText(model.getAdult() + model.getTagline() + model.getRuntime());


        sheetRecyclerView = view.findViewById(R.id.design_bottom_sheet);
        coordinatorLayout = view.findViewById(R.id.main_content);
        fab = view.findViewById(R.id.fab);

        getTrailers_btn = view.findViewById(R.id.get_trailers);
        getReview_btn = view.findViewById(R.id.get_reviews);


        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String json = getActivity().getIntent().getStringExtra("json");
        model = new Gson().fromJson(json, MovieModel.class);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        behavior = BottomSheetBehavior.from(sheetRecyclerView);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);


        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        apiInterface.getMovieDetails(model.getId(), Constants.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                MovieModel model = response.body();
                bind(model);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });


        getTrailers_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                trailerAdapter = new TrailerAdapter(trailerList, getContext());
                sheetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                sheetRecyclerView.setAdapter(trailerAdapter);

                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });


        getReview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reviewAdapter = new ReviewAdapter(reviewList);
                sheetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                sheetRecyclerView.setAdapter(reviewAdapter);

                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
            }
        });


        apiInterface.getYoutubeKey(model.getId(), Constants.API_KEY).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (response.isSuccessful()) {
                    trailerList = response.body();
                }
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {
            }
        });


        apiInterface.getReviews(model.getId(), Constants.API_KEY).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                reviewList = response.body();
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });
    }

    private void bind(MovieModel model) {
        title_tv.setText(model.getTitle());
        synopsis_tv.setText(model.getTagline());
        releaseDate_tv.setText(model.getRelease_date() + " (Release Date)");
        duration_tv.append(model.getRuntime() + " min");

        vote_avg_tv.setText(String.valueOf(model.getVote_average()));
        vote_count_tv.setText(model.getVote_count() + " votes");
        String pop = String.format("%.1f", model.getPopularity());
        pop_tv.setText(pop);
        lang_tv.setText(model.getOriginal_language());


        overview_tv.setText(model.getOverview());


        Picasso.with(getContext()).load(Constants.IMAGE_BASE_URL + model.getPoster_path()).into(poster);
        Picasso.with(getContext()).load(Constants.BACKDROP_BASE_URL + model.getBackdrop_path()).into(backDrop);

    }
}
