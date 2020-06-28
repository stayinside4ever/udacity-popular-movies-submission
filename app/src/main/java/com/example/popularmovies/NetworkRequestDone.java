package com.example.popularmovies;

import com.example.popularmovies.database.MovieEntity;

import java.util.List;

public interface NetworkRequestDone {
     public void onMoviesFetched(List<MovieEntity> movies);
}
