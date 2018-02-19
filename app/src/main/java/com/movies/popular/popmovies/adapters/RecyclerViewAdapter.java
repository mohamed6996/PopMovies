package com.movies.popular.popmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.popular.popmovies.MovieList;
import com.movies.popular.popmovies.MovieModel;
import com.movies.popular.popmovies.R;

/**
 * Created by lenovo on 2/19/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    MovieList movieList;

    public RecyclerViewAdapter(MovieList movieList) {
        this.movieList = movieList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movieModel = movieList.getResults().get(position);
        Log.i("what",movieModel.getTitle());
        holder.textview.setText(movieModel.getOriginal_title());
    }

    @Override
    public int getItemCount() {
        return movieList.getResults().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview;

        public ViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.textview);
        }
    }
}
