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

package com.movies.popular.popmovies.topRated;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.PagedList;

import com.movies.popular.popmovies.model.MovieModel;
import com.movies.popular.popmovies.popular.PopularRepository;

/**
 * Created by lenovo on 2/21/2018.
 */

public class TopRatedViewModel extends ViewModel {


    LiveData<PagedList<MovieModel>> listLiveData;
    TopRatedRepository topRatedRepository;

    public LiveData<PagedList<MovieModel>> getLiveData() {
        if (topRatedRepository == null) {
            topRatedRepository = new TopRatedRepository();
            listLiveData = topRatedRepository.getPopularMovies();

        }
        return listLiveData;
    }

}