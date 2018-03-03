package com.movies.popular.popmovies.room;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.movies.popular.popmovies.model.MovieModel;

import java.util.List;

/**
 * Created by lenovo on 2/28/2018.
 */

@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorites_table")
    DataSource.Factory<Integer, MovieModel> getAllFavourites();

    @Query("SELECT id FROM favorites_table WHERE id = :id")
    String getFavouriteMovie(String id);

    @Insert
    long insertFavoriteMovie(MovieEntity movieEntity);

    @Query("DELETE FROM favorites_table WHERE id = :id")
    int deleteFavoriteMovie(long id);

//    @Query("DELETE FROM favorites_table WHERE id = :id")
//    void delete(String id);

//    @Insert
//    void insert(MovieEntity movieEntity);


}
