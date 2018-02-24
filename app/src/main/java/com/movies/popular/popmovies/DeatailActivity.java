package com.movies.popular.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.movies.popular.popmovies.model.MovieModel;

public class DeatailActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deatail);

    //    Bundle bundle = getIntent().getExtras();
    //    Log.i("bundle", bundle.getString("movie_title"));

        DetailFragment detailFragment = new DetailFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.detail_fragment_container, detailFragment, "detailFragment").commit();


    }


}
