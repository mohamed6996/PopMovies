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

package com.movies.popular.popmovies.room;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.movies.popular.popmovies.Constants;
import com.movies.popular.popmovies.room.MovieDao;
import com.movies.popular.popmovies.room.MovieEntity;
import com.movies.popular.popmovies.room.MovieRepository;

/**
 * Created by lenovo on 3/1/2018.
 */

public class FavoriteProvider extends ContentProvider {

    /*
    * reference
    *  google android architecture components sample
    *  https://github.com/googlesamples/android-architecture-components/tree/master/PersistenceContentProviderSample
    * */



    public static final String AUTHORITY = "com.movies.popular.popmovies.room";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Constants.TABLE_NAME);

    public static final int FAVORITES = 100;
    public static final int FAVORITE_WITH_ID = 101;

    public static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, Constants.TABLE_NAME, FAVORITES);
        uriMatcher.addURI(AUTHORITY, Constants.TABLE_NAME + "/*", FAVORITE_WITH_ID);
        return uriMatcher;
    }



    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        int match = sUriMatcher.match(uri);
        Context context = getContext();
        MovieRepository movieRepository = new MovieRepository(getContext());
        Cursor cursor;

        switch (match) {
            case FAVORITES:
                break;
            case FAVORITE_WITH_ID:
                break;
        }


        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        int match = sUriMatcher.match(uri);


        switch (match) {

            case FAVORITES:
                Context context = getContext();
                MovieRepository movieRepository = new MovieRepository(getContext());
                MovieEntity movieEntity = new Gson().fromJson(contentValues.getAsString("movie_entity"), MovieEntity.class);
                long id = movieRepository.insertById(movieEntity);
                context.getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);

            case FAVORITE_WITH_ID:
                throw new IllegalArgumentException("Invalid URI, cannot insert with ID: " + uri);

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case FAVORITE_WITH_ID:

                Context context = getContext();
                MovieRepository movieRepository = new MovieRepository(getContext());
                int count = movieRepository.removeById(ContentUris.parseId(uri));
                context.getContentResolver().notifyChange(uri, null);
                return count;
            case FAVORITES:
                throw new IllegalArgumentException("Invalid URI, cannot delete without ID" + uri);
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
