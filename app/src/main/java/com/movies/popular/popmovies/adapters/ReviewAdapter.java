package com.movies.popular.popmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.movies.popular.popmovies.ListItemClickListener;
import com.movies.popular.popmovies.R;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.model.TrailerList;
import com.movies.popular.popmovies.model.TrailerModel;

/**
 * Created by lenovo on 2/24/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private TrailerList reviewList;

    public ReviewAdapter(TrailerList reviewList) {
        this.reviewList = reviewList;

    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return reviewList.getResults().size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder  {
        TextView textView;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.review_item);
        }

        public void bind(int position) {
            TrailerModel model = reviewList.getResults().get(position);
            textView.setText(model.getContent());
        }


    }
}
