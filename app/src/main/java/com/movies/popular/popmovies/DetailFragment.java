package com.movies.popular.popmovies;


import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;

import android.content.Loader;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.picassopalette.PicassoPalette;
import com.google.gson.Gson;
import com.movies.popular.popmovies.adapters.ReviewAdapter;
import com.movies.popular.popmovies.adapters.TrailerAdapter;
import com.movies.popular.popmovies.api.ApiClient;
import com.movies.popular.popmovies.api.ApiInterface;
import com.movies.popular.popmovies.interfaces.ListItemClickListener;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.model.TrailerList;
import com.movies.popular.popmovies.room.FavoriteProvider;
import com.movies.popular.popmovies.room.MovieEntity;
import com.movies.popular.popmovies.room.MovieRepository;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements ListItemClickListener, LoaderManager.LoaderCallbacks<String> {

    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    TrailerList trailerList, reviewList;
    MovieModel model;
    MovieRepository movieRepository;
    ApiInterface apiInterface;
    BottomSheetBehavior behavior;
    LayerDrawable layerDrawable;
    GradientDrawable gradientDrawable;
    Drawable fabDrawable;
    Drawable newDrawable;
    boolean isFavorite = false;
    String movie_id;
    ListItemClickListener listener = (ListItemClickListener) this;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @BindView(R.id.back_drop)
    ImageView backDrop;
    @BindView(R.id.detail_poster_iv)
    ImageView poster;
    @BindView(R.id.detail_title_tv)
    TextView title_tv;
    @BindView(R.id.detail_synopsis_tv)
    TextView synopsis_tv;
    @BindView(R.id.detail_release_date_tv)
    TextView releaseDate_tv;
    @BindView(R.id.detail_duration)
    TextView duration_tv;
    @BindView(R.id.detail_vote_avg)
    TextView vote_avg_tv;
    @BindView(R.id.detail_vote_count)
    TextView vote_count_tv;
    @BindView(R.id.detail_pop)
    TextView pop_tv;
    @BindView(R.id.detail_lang)
    TextView lang_tv;
    @BindView(R.id.detail_overview)
    TextView overview_tv;
    @BindView(R.id.get_reviews)
    Button getReview_btn;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.trailer_rec_view)
    RecyclerView trailerRecyclerView;
    @BindView(R.id.design_bottom_sheet)
    RecyclerView sheetRecyclerView;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (MainActivity.mTWO_PANE) {
            movie_id = getArguments().getString("movie_id", "");
        } else {
            movie_id = getActivity().getIntent().getExtras().getString("movie_id", "");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        movieRepository = new MovieRepository(getActivity().getApplication());
        behavior = BottomSheetBehavior.from(sheetRecyclerView);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        layerDrawable = (LayerDrawable) ContextCompat.getDrawable(getContext(), R.drawable.ic_circle);
        gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.circle_shape);
        fabDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_fav);
        newDrawable = fabDrawable.getConstantState().newDrawable();
        // init loader manager
        getActivity().getLoaderManager().initLoader(1, null, this).forceLoad();
        // retrofit
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        apiInterface.getMovieDetails(movie_id, Constants.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                model = response.body();
                bind(model);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
            }
        });


        apiInterface.getYoutubeKey(movie_id, Constants.API_KEY).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (response.isSuccessful()) {
                    trailerList = response.body();
                    prepareTrailers(trailerList);
                }
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {
            }
        });

        apiInterface.getReviews(movie_id, Constants.API_KEY).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (response.isSuccessful()) {
                    prepareReviews(response.body());
                }
            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {
            }
        });


        getReview_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN)
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                else
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFavorite();
            }
        });


    }

    private void prepareTrailers(TrailerList trailerList) {
        trailerAdapter = new TrailerAdapter(trailerList, getContext(), listener);
        LinearLayoutManager horizontal = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setLayoutManager(horizontal);
        trailerRecyclerView.setAdapter(trailerAdapter);
    }

    private void prepareReviews(TrailerList trailerList) {
        reviewList = trailerList;
        if (reviewList.getResults().size() == 0) {
            getReview_btn.setVisibility(View.GONE);
        } else {
            getReview_btn.setVisibility(View.VISIBLE);
            reviewAdapter = new ReviewAdapter(reviewList);
            sheetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            sheetRecyclerView.setAdapter(reviewAdapter);
        }
    }

    private void checkFavorite() {
        try {
            if (isFavorite) { // if its already fav, delete it, otherwise add it
                //  movieRepository.removeFavorite(movie_id);
                Uri uri = FavoriteProvider.CONTENT_URI.buildUpon().appendPath(movie_id).build();
                getContext().getContentResolver().delete(uri, null, null);

                newDrawable.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                isFavorite = false;
                Snackbar.make(fab, "Removed from favorites", Snackbar.LENGTH_SHORT).show();
            } else {
                MovieEntity movieEntity = new MovieEntity(model.getPoster_path(), model.getOverview(), model.getRelease_date(), model.getOriginal_title(),
                        model.getOriginal_language(), model.getTitle(), model.getBackdrop_path(), model.getId(), model.getVote_count(), model.getVote_average(),
                        model.getPopularity(), model.getTagline(), model.getAdult(), model.getRuntime());

                ContentValues contentValues = prepareContentValue(movieEntity);
                Uri uri = FavoriteProvider.CONTENT_URI;
                getContext().getContentResolver().insert(uri, contentValues);

              //  movieRepository.insert(movieEntity);
                newDrawable.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                isFavorite = true;
                Snackbar.make(fab, "Added to favorites", Snackbar.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("error", e.getMessage());
            Snackbar.make(fab, "Something went wrong", Snackbar.LENGTH_SHORT).show();
        }
    }

    private ContentValues prepareContentValue(MovieEntity movieEntity) {
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String entity = gson.toJson(movieEntity);
        contentValues.put("movie_entity", entity);
        return contentValues;
    }

    private void bind(final MovieModel model) {
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

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) scrollRange = appBarLayout.getTotalScrollRange();
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(model.getTitle());
                    isShown = true;
                } else if (isShown) {
                    collapsingToolbar.setTitle("");
                    isShown = false;
                }

            }
        });


        Picasso.with(getContext()).load(Constants.IMAGE_BASE_URL + model.getPoster_path()).into(poster);

        String image_url = Constants.BACKDROP_BASE_URL + model.getBackdrop_path();

        PicassoPalette.with(image_url, backDrop);

        Picasso.with(getContext()).load(image_url)
                .into(backDrop, PicassoPalette.with(image_url, backDrop)
                        .use(PicassoPalette.Profile.VIBRANT)
                        .intoCallBack(
                                new PicassoPalette.CallBack() {
                                    @Override
                                    public void onPaletteLoaded(Palette palette) {
                                        int color;
                                        try {
                                            if (palette.getDarkVibrantSwatch() != null) {
                                                color = palette.getDarkVibrantSwatch().getRgb();
                                                gradientDrawable.setColor(color);
                                                vote_avg_tv.setBackground(layerDrawable);
                                                pop_tv.setBackground(layerDrawable);
                                                lang_tv.setBackground(layerDrawable);
                                            }
                                        } catch (Exception e) {
                                            Toast.makeText(getContext(), "drawable error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                })

                );


    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        String key = trailerList.getResults().get(clickedItemIndex).getKey();
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key));
            startActivity(intent);
        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return movieRepository.getFavoriteMovie(getContext(), movie_id);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        if (movie_id.equals(s)) {
            newDrawable.mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
            isFavorite = true;
        } else {
            newDrawable.mutate().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            isFavorite = false;
        }
        fab.setImageDrawable(newDrawable);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
    }

}
