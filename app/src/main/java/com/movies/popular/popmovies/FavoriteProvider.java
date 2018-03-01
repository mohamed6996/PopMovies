//package com.movies.popular.popmovies;
//
//import android.content.ContentProvider;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.net.Uri;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//
//import com.movies.popular.popmovies.room.MovieDao;
//import com.movies.popular.popmovies.room.MovieRepository;
//
///**
// * Created by lenovo on 3/1/2018.
// */
//
//public class FavoriteProvider extends ContentProvider {
//
//    public static final String AUTHORITY = " com.movies.popular.popmovies";
//
//    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + Constants.TABLE_NAME);
//
//    public static final int FAVORITES = 100;
//    public static final int FAVORITE_WITH_ID = 101;
//
//    public static final UriMatcher sUriMatcher = buildUriMatcher();
//
//    public static UriMatcher buildUriMatcher() {
//        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
//        uriMatcher.addURI(AUTHORITY, Constants.TABLE_NAME, FAVORITES);
//        uriMatcher.addURI(AUTHORITY, Constants.TABLE_NAME + "/*", FAVORITE_WITH_ID);
//        return uriMatcher;
//    }
//
//    @Override
//    public boolean onCreate() {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
//        int match = sUriMatcher.match(uri);
//        Context context = getContext();
//        MovieRepository movieRepository = new MovieRepository(context);
//        final Cursor cursor;
//
//        switch (match) {
//            case FAVORITES:
//                cursor = movieRepository.getAllFavorites();
//                break;
//            case FAVORITE_WITH_ID:
//                break;
//        }
//
//
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public String getType(@NonNull Uri uri) {
//        return null;
//    }
//
//    @Nullable
//    @Override
//    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
//        return null;
//    }
//
//    @Override
//    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
//        return 0;
//    }
//
//    @Override
//    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
//        return 0;
//    }
//}
