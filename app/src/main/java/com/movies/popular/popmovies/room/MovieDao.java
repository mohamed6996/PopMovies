package com.movies.popular.popmovies.room;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.movies.popular.popmovies.model.MovieModel;

import java.util.List;

/**
 * Created by lenovo on 2/28/2018.
 */

@Dao
public interface MovieDao {

    @Insert
    void insert(MovieEntity movieEntity);

    @Query("DELETE FROM favorites_table WHERE id = :id")
    void delete(String id);

    @Query("SELECT * FROM favorites_table")
    public abstract DataSource.Factory<Integer, MovieModel> getAllFavourites();

    @Query("SELECT id FROM favorites_table WHERE id = :id")
    public String getFavouriteMovie(String id);

}
