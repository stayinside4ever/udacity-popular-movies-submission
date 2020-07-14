package com.example.popularmovies.network;

import com.example.popularmovies.database.MovieEntity;

import java.util.List;

public interface MovieNetworkRequestDone {
     void onMoviesFetched(List<MovieEntity> movies);
     void onRequestFailed();
}
