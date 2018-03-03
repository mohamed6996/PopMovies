/*
 * This project was submitted by Mohamed Ali as part of the Nanodegree At Udacity.
 *
 * As part of Udacity Honor code, your submissions must be your own work, hence
 * submitting this project as yours will cause you to break the Udacity Honor Code
 * and the suspension of your account.
 *
 * You could use the code as a reference, but if
 * you submit it, it's your own responsibility if you get expelled.
 *
 * Copyright (c) 2018 Mohamed Ali
 *
 * Besides the above notice, the following license applies and this license notice
 * must be included in all works derived from this project.
 *
 *
 * MIT License
 *
 * Copyright (c) 2018 Mohamed Ali
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.movies.popular.popmovies.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movies.popular.popmovies.Constants;
import com.movies.popular.popmovies.interfaces.ListItemClickListener;
import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.R;
import com.squareup.picasso.Picasso;

/**
 * Created by lenovo on 2/19/2018.
 */


public class RecyclerViewAdapter extends PagedListAdapter<MovieModel, RecyclerView.ViewHolder> {

    Context context;
    ListItemClickListener listener;

    public RecyclerViewAdapter(Context context, ListItemClickListener listener) {
        super(RecyclerViewAdapter.DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final RecyclerView.ViewHolder holder;
        View view;

        switch (viewType) {
            case R.layout.movie_item_1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_1, parent, false);
                holder = new ViewHolderOne(view);
                break;
            case R.layout.movie_item_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_2, parent, false);
                holder = new ViewHolderTwo(view);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_1, parent, false);
                holder = new ViewHolderOne(view);
                break;
        }

        return holder;

    }

    // https://www.youtube.com/watch?v=blwB8GL4vWw
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderOne) {
            ((ViewHolderOne) holder).bind(position);
        } else if (holder instanceof ViewHolderTwo) {
            ((ViewHolderTwo) holder).bind(position);
        } else {
            ((ViewHolderOne) holder).bind(position);
        }
    }


    @Override
    public int getItemViewType(int position) {
        int column_count = context.getResources().getInteger(R.integer.grid_column_count);
        switch (column_count) {
            case 2:
                if (position % 3 == 0)
                    return R.layout.movie_item_2;
                else
                    return R.layout.movie_item_1;
            case 3:
                if (position % 4 == 0)
                    return R.layout.movie_item_2;
                else
                    return R.layout.movie_item_1;
            case 6:
                if (position % 5 == 0)
                    return R.layout.movie_item_2;
                else
                    return R.layout.movie_item_1;
            default:
                if (position % 3 == 0)
                    return R.layout.movie_item_2;
                else
                    return R.layout.movie_item_1;
        }

    }

    public static final DiffCallback<MovieModel> DIFF_CALLBACK = new DiffCallback<MovieModel>() {
        @Override
        public boolean areItemsTheSame(@NonNull MovieModel oldProduct, @NonNull MovieModel newProduct) {

            return oldProduct.getId() == newProduct.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieModel oldProduct, @NonNull MovieModel newProduct) {

            return oldProduct.equals(newProduct);
        }
    };

    class ViewHolderOne extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textview;
        ImageView imageView;

        public ViewHolderOne(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.movie_title);
            imageView = itemView.findViewById(R.id.movie_image_view);

            itemView.setOnClickListener(this);

        }

        void bind(int position) {
            MovieModel model = getItem(position);
            textview.setText(model.getTitle());
            String imageUrl = Constants.IMAGE_BASE_URL + model.getPoster_path();
            Picasso.with(context).load(imageUrl).into(imageView);
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView movieTitle, movieDesc;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_image_view_2);
            movieTitle = itemView.findViewById(R.id.movie_title_text_view_2);
            movieDesc = itemView.findViewById(R.id.movie_desc_text_view_2);

            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            MovieModel model = getItem(position);
            String imageUrl = Constants.IMAGE_BASE_URL + model.getPoster_path();
            Picasso.with(context).load(imageUrl).into(imageView);

            movieTitle.setText(model.getTitle());
            movieDesc.setText(model.getOverview());
        }

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getAdapterPosition());
        }
    }

}