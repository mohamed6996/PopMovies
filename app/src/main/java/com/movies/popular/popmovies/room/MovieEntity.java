package com.movies.popular.popmovies.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.movies.popular.popmovies.Constants;

/**
 * Created by lenovo on 2/28/2018.
 */

@Entity(tableName = Constants.TABLE_NAME)
public class MovieEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String poster_path;
    private String overview;
    private String release_date;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private int vote_count;
    private float vote_average;
    private float popularity;
    private String tagline;
    private String adult;
    private int runtime;

    public MovieEntity(String poster_path, String overview, String release_date, String original_title, String original_language,
                       String title, String backdrop_path, String id, int vote_count, float vote_average, float popularity,
                       String tagline, String adult, int runtime) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.id = id;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.tagline = tagline;
        this.adult = adult;
        this.runtime = runtime;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getId() {
        return id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public float getVote_average() {
        return vote_average;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTagline() {
        return tagline;
    }

    public String getAdult() {
        return adult;
    }

    public int getRuntime() {
        return runtime;
    }
}
