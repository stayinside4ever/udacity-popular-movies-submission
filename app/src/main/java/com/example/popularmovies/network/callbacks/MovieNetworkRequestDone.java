package com.example.popularmovies.network.callbacks;

import com.example.popularmovies.database.models.MovieEntity;

import java.util.List;

public interface MovieNetworkRequestDone {
     void onMoviesFetched(List<MovieEntity> movies);
     void onRequestFailed();
}
