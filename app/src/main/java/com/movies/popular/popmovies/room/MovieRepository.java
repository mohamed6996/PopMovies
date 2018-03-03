package com.movies.popular.popmovies.room;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import com.movies.popular.popmovies.model.MovieModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by lenovo on 2/28/2018.
 */

public class MovieRepository {

    private static MovieDao movieDao;
    private LiveData<PagedList<MovieModel>> allMovies;
    private static int returnFromRemove;
    private static long returnFromInsert;


    public MovieRepository(Context context) {
        MovieDatabase db = MovieDatabase.getDatabase(context);
        this.movieDao = db.movieDao();
        this.allMovies = new LivePagedListBuilder<>(movieDao.getAllFavourites(), 20).build();
    }


    //******************************************************************************


    public Long insertById(MovieEntity movieEntity) {
        new insertAsync(movieDao).execute(movieEntity);
        return returnFromInsert;
    }


    private static class insertAsync extends AsyncTask<MovieEntity, Void, Long> {

        private MovieDao mAsyncTaskDao;

        insertAsync(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final MovieEntity... params) {

            return mAsyncTaskDao.insertFavoriteMovie(params[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            returnFromInsert = aLong;
        }
    }


    public int removeById(Long id) {

        new removeAsync(movieDao).execute(id);
        return returnFromRemove;

    }

    private static class removeAsync extends AsyncTask<Long, Void, Integer> {

        private MovieDao mAsyncTaskDao;

        removeAsync(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Integer doInBackground(final Long... params) {
            return mAsyncTaskDao.deleteFavoriteMovie(params[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            returnFromRemove = integer;
        }
    }


    //******************************************************************************


    public LiveData<PagedList<MovieModel>> getAllFavorites() {
        return allMovies;
    }


    public isFavoriteTask getFavoriteMovie(Context context, String id) {
        return new isFavoriteTask(context, id);
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


//    public void insert(MovieEntity model) {
//        new insertAsyncTask(movieDao).execute(model);
//    }
//
//    public void removeFavorite(String id) {
//        new removeAsyncTask(movieDao).execute(id);
//    }


//    private static class insertAsyncTask extends AsyncTask<MovieEntity, Void, Void> {
//
//        private MovieDao mAsyncTaskDao;
//
//        insertAsyncTask(MovieDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final MovieEntity... params) {
//            mAsyncTaskDao.insert(params[0]);
//            return null;
//        }
//    }

//    private static class removeAsyncTask extends AsyncTask<String, Void, Void> {
//
//        private MovieDao mAsyncTaskDao;
//
//        removeAsyncTask(MovieDao dao) {
//            mAsyncTaskDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(final String... params) {
//            mAsyncTaskDao.delete(params[0]);
//            return null;
//        }
//    }

}
