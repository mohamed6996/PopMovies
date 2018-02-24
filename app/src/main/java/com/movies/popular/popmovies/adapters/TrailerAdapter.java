package com.movies.popular.popmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.popular.popmovies.Constants;
import com.movies.popular.popmovies.R;
import com.movies.popular.popmovies.model.TrailerList;
import com.movies.popular.popmovies.model.TrailerModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lenovo on 2/23/2018.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    TrailerList trailerList;
    Context context;

    public TrailerAdapter(TrailerList trailerList, Context context) {
        this.trailerList = trailerList;
        this.context = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return trailerList.getResults().size();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView movieName, trailerType, trailerQuality;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.trailer_iv);
            movieName = itemView.findViewById(R.id.trailer_movie_name);
            trailerType = itemView.findViewById(R.id.trailer_type);
            trailerQuality = itemView.findViewById(R.id.trailer_quality);

        }

        void bind(int position) {
            TrailerModel model = trailerList.getResults().get(position);
            movieName.setText(model.getName());
            trailerType.append(model.getSite());
            trailerQuality.append(String.valueOf(model.getSize()) + "p");
            Picasso.with(context).load(Constants.THUMBNAIL_BASE_URL + model.getKey() + "/0.jpg").into(thumbnail);
        }
    }
}
