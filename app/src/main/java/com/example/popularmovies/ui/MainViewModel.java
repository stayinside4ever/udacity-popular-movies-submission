package com.example.popularmovies.ui;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.database.AppDatabase;
import com.example.popularmovies.database.models.MovieEntity;
import com.example.popularmovies.network.NetworkUtils;
import com.example.popularmovies.network.callbacks.MovieNetworkRequestDone;
import com.example.popularmovies.network.callbacks.MovieRequestFailed;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<MovieEntity>> movieList = new MutableLiveData<>();
    private int currentPos = 0;
    private AppDatabase database;
    private NetworkUtils networkUtils;

    public MainViewModel(Application application, final MovieRequestFailed reqFailCallback) {
        database = AppDatabase.getInstance(application);
        networkUtils = new NetworkUtils(new MovieNetworkRequestDone() {
            @Override
            public void onMoviesFetched(List<MovieEntity> movies) {
                movieList.setValue(movies);
            }

            @Override
            public void onRequestFailed() {
                reqFailCallback.moviesRequestFailed();
            }
        });
    }

    public LiveData<List<MovieEntity>> getMovies() {
        return movieList;
    }

    public void makeRepositoryRequest(int tabPosition) {
        currentPos = tabPosition;
        movieList.postValue(null);
        switch (tabPosition) {
            case 0:
                networkUtils.getPopularMoviesFromNetwork();
                break;
            case 1:
                networkUtils.getTopRatedMoviesFromNetwork();
                break;
            case 2:
                database.moviesDao().loadAllFavourites().observeForever(new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(List<MovieEntity> movieEntities) {
                        database.moviesDao().loadAllFavourites().removeObserver(this);
                        if (movieEntities == null || movieEntities.isEmpty()) {
                            List<MovieEntity> noMovies = new ArrayList<>();
                            MovieEntity emptyMovie = new MovieEntity();
                            emptyMovie.setId(-1);
                            noMovies.add(emptyMovie);
                            movieList.setValue(noMovies);
                        } else {
                            movieList.setValue(movieEntities);
                        }
                    }
                });
        }
    }

    public int getCurrentPos() {
        return currentPos;
    }

}
