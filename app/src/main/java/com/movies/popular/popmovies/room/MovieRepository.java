package com.movies.popular.popmovies.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import com.movies.popular.popmovies.model.MovieModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2/28/2018.
 */

public class MovieRepository {

    private static MovieDao movieDao;
    private LiveData<PagedList<MovieModel>> allMovies;
    private MutableLiveData<String> mutableLiveData = new MutableLiveData<>();
    private Executor IO_EXECUTOR;


    public MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        this.movieDao = db.movieDao();
        this.allMovies = new LivePagedListBuilder<>(movieDao.getAllFavourites(), 20).build();
        IO_EXECUTOR = Executors.newSingleThreadExecutor();
    }


    public LiveData<PagedList<MovieModel>> getAllFavorites() {
        return allMovies;
    }




    public isFavoriteTask getFavoriteMovie(Context context, String id) {
        return new isFavoriteTask(context, id);
    }




    public void insert(MovieEntity model) {

          new insertAsyncTask(movieDao).execute(model);
    }

    public void removeFavorite(String id){
        new removeAsyncTask(movieDao).execute(id);
    }


    private static class isFavoriteTask extends AsyncTaskLoader<String> {
        String id;

        public isFavoriteTask(Context context, String id) {
            super(context);
            this.id = id;
        }

        @Override
        public String loadInBackground() {
            return movieDao.getFavouriteMovie(id);
        }
    }

    private class queryAsyncTask extends AsyncTask<String, Void, String> {
        private MovieDao mAsyncTaskDao;

        queryAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected String doInBackground(String... strings) {
            return mAsyncTaskDao.getFavouriteMovie(strings[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            mutableLiveData.setValue(s);
        }
    }

    private static class insertAsyncTask extends AsyncTask<MovieEntity, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class removeAsyncTask extends AsyncTask<String, Void, Void> {

        private MovieDao mAsyncTaskDao;

        removeAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

}
